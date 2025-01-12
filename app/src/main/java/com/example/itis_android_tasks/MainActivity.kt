package com.example.itis_android_tasks

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.itis_android_tasks.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import android.provider.Settings

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private var isImageLoaded = false
    private var currentTheme = R.style.Theme_Itis_android_tasks

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            imageUri?.let {
                binding.imageView.setImageURI(it)
                isImageLoaded = true
                binding.closeButton.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            currentTheme = savedInstanceState.getInt(
                KEY_SELECTED_THEME,
                R.style.Theme_Itis_android_tasks
            )
        }

        setTheme(currentTheme)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        if (intent.getBooleanExtra(EXTRA_FROM_NOTIFICATION, false)) {
            showSnackbar(getString(R.string.from_notification))
            intent.removeExtra(EXTRA_FROM_NOTIFICATION)
        }

        setupSpinner()

        checkNotificationPermission()

        binding.imageView.setOnClickListener {
            if (isImageLoaded) {
                showSnackbar(getString(R.string.image_already_download))
            } else {
                checkStoragePermissionAndPickImage()
            }
        }

        binding.closeButton.setOnClickListener {
            resetImage()
        }

        binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.colorButton1.visibility = View.VISIBLE
                binding.colorButton2.visibility = View.VISIBLE
                binding.colorButton3.visibility = View.VISIBLE
            } else {
                binding.colorButton1.visibility = View.GONE
                binding.colorButton2.visibility = View.GONE
                binding.colorButton3.visibility = View.GONE
            }
        }

        binding.colorButton1.setOnClickListener {
            applyTheme(R.style.AppTheme_Red)
        }
        binding.colorButton2.setOnClickListener {
            applyTheme(R.style.AppTheme_Green)
        }
        binding.colorButton3.setOnClickListener {
            applyTheme(R.style.AppTheme_Yellow)
        }

        binding.resetColorButton.setOnClickListener {
            applyTheme(R.style.Theme_Itis_android_tasks)
        }

        binding.showNotificationButton.setOnClickListener {
            showNotification()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_THEME, currentTheme)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2 -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    showPermissionDeniedDialog()
                }
            }
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf(
                getString(R.string.max),
                getString(R.string.high),
                getString(R.string.priority_default),
                getString(R.string.low)
            )
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter
    }

    private fun resetImage() {
        binding.imageView.setImageResource(0)
        isImageLoaded = false
        binding.closeButton.visibility = View.GONE
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.imageView, message, Snackbar.LENGTH_LONG).show()
    }

    private fun applyTheme(themeResId: Int) {
        currentTheme = themeResId
        setTheme(themeResId)
        recreate()
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }

    private fun checkStoragePermissionAndPickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                    2
                )
            } else {
                pickImageFromGallery()
            }
        } else
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    2
                )
            } else {
                pickImageFromGallery()
            }
    }

    private fun showPermissionDeniedDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_required))
            .setMessage(getString(R.string.storage_permission_denied_message))
            .setPositiveButton(getString(R.string.go_to_settings)) { dialog, _ ->
                openAppSettings()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun showNotification() {
        val title = binding.notificationTitle.text.toString()
        val message = binding.notificationText.text.toString()
        val priority = binding.prioritySpinner.selectedItemPosition

        if (title.isEmpty()) {
            showSnackbar(getString(R.string.fill_in_the_header))
        } else if (message.isEmpty()) {
            showSnackbar(getString(R.string.fill_in_the_notification))
        } else {
            createNotification(title, message, priority)
        }
    }

    private fun createNotification(title: String, message: String, priority: Int) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.default_channel_id)
            val channelName = getString(R.string.default_channel_name)
            val importance = when (priority) {
                3 -> NotificationManager.IMPORTANCE_LOW
                2 -> NotificationManager.IMPORTANCE_DEFAULT
                1 -> NotificationManager.IMPORTANCE_HIGH
                0 -> NotificationManager.IMPORTANCE_MAX
                else -> NotificationManager.IMPORTANCE_DEFAULT
            }
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(EXTRA_FROM_NOTIFICATION, true)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            this,
            getString(R.string.default_channel_id)
        )
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    companion object {
        private const val KEY_SELECTED_THEME = "selected_theme"
        private const val EXTRA_FROM_NOTIFICATION = "from_notification"
    }
}