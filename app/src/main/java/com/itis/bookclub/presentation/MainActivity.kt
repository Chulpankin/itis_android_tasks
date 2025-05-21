package com.itis.bookclub.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.itis.bookclub.R
import com.itis.bookclub.presentation.utils.Navigator
import com.itis.bookclub.util.appComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    private var navController: NavController? = null

    private val fragmentContainerId = R.id.fv_root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        appComponent.inject(activity = this)

        initNavigation()
    }

    private fun initNavigation() {
        navController = (supportFragmentManager
            .findFragmentById(R.id.fv_root) as NavHostFragment)
            .navController

        navigator.attachNavController(navController)
    }
}