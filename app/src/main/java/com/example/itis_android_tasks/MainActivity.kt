package com.example.itis_android_tasks

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.itis_android_tasks.databinding.ActivityMainBinding
import com.example.itis_android_tasks.utils.Keys

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fv_root) as NavHostFragment
        val navController = navHostFragment.navController
        val sharedPreferences = getSharedPreferences(Keys.PREFS_NAME, Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt(Keys.KEY_USER_ID, -1)

        if (savedInstanceState == null) {
            if (userId == -1) {
                navController.navigate(R.id.loginFragment)
            } else {
                navController.navigate(R.id.filmsFragment)
            }
        }

        binding.bnvMain.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
