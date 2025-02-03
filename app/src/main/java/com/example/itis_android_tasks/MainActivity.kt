package com.example.itis_android_tasks

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.itis_android_tasks.model.*
import com.example.itis_android_tasks.ui.CoroutineSimulatorScreen
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private var jobs: MutableList<Job> = mutableListOf()
    private var isRunning by mutableStateOf(false)
    private var cancellationPolicy: CancellationPolicy = CancellationPolicy.CONTINUE_ON_PAUSE

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        showToast(getString(R.string.error, throwable.message))
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showToast(getString(R.string.notification_permission_granted))
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    showPermissionDeniedDialog()
                }
            } else {
                showToast(getString(R.string.notification_permission_permanently_denied))
                showOpenSettingsDialog()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

        setContent {
            CoroutineSimulatorScreen(
                onStartCoroutines = { count, executionMode, cancellationPolicy, dispatcher ->
                    startCoroutines(count, executionMode, cancellationPolicy, dispatcher)
                },
                onCancelCoroutines = { cancelCoroutines() },
                isRunning = isRunning
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showToast(getString(R.string.notification_permission_already_granted))
        } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showPermissionDeniedDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_required))
            .setMessage(getString(R.string.notification_permission_desc))
            .setPositiveButton(getString(R.string.grant)) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showOpenSettingsDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_denied))
            .setMessage(getString(R.string.permission_denied_desc))
            .setPositiveButton(getString(R.string.open_settings)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts(PACKAGE, packageName, null)
                }
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                getString(R.string.coroutine_worker),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = getString(R.string.shows_notifications)
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(message: String) {
        val notificationManager = getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.coroutine_worker))
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun startCoroutines(
        count: Int,
        executionMode: ExecutionMode,
        cancellationPolicy: CancellationPolicy,
        dispatcher: AppDispatchers
    ) {
        isRunning = true
        this.cancellationPolicy = cancellationPolicy
        jobs.clear()

        when (executionMode) {
            ExecutionMode.SEQUENTIAL -> {
                lifecycleScope.launch(dispatcher.dispatcher + exceptionHandler) {
                    repeat(count) {
                        val job = lifecycleScope.launch {
                            startCoroutineLogic()
                        }
                        jobs.add(job)
                        job.join()
                    }
                    isRunning = false
                }
            }
            ExecutionMode.PARALLEL -> {
                repeat(count) {
                    val job =
                        lifecycleScope.launch(dispatcher.dispatcher + exceptionHandler) {
                            startCoroutineLogic()
                        }
                    jobs.add(job)
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showNotification(getString(R.string.coroutines_are_running_in_the_background))
        }
    }

    private fun cancelCoroutines() {
        var cancelledCoroutinesCount = 0
        jobs.forEach { job ->
            if (job.isActive) {
                job.cancel()
                cancelledCoroutinesCount++
            }
        }
        jobs.clear()
        isRunning = false

        showToast(getString(R.string.coroutines_were_cancelled, cancelledCoroutinesCount.toString()))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showNotification(
                getString(
                    R.string.some_coroutines_were_cancelled,
                    cancelledCoroutinesCount.toString()
                ))
        }
    }

    private suspend fun startCoroutineLogic() {
        delay(10000)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        if (cancellationPolicy == CancellationPolicy.CANCEL_ON_PAUSE) {
            cancelCoroutines()
        }
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "coroutine_worker_channel"
        private const val NOTIFICATION_ID = 1
        private const val PACKAGE = "package"
    }
}