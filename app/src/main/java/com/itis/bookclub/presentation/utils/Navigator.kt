package com.itis.bookclub.presentation.utils

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.itis.bookclub.R
import javax.inject.Inject

class Navigator @Inject constructor() {

    private var navController: NavController? = null

    fun attachNavController(navController: NavController?) {
        this.navController = navController
    }

    fun detachNavController(navController: NavController?) {
        if (this.navController == navController) {
            this.navController = null
        }
    }

    fun popBackStack() {
        navController?.popBackStack()
    }

    fun navigateToMain() {
        navController?.let { nc ->
            nc.navigate(R.id.bookListFragment)
        }
    }

    fun navigateToSignUp() {
        navController?.navigate(R.id.signUpFragment)
    }

    fun navigateToSignIn() {
        navController?.let { nc ->
            val options = nc.currentDestination?.id?.let {
                NavOptions.Builder()
                    .setPopUpTo(it, inclusive = true)
                    .build()
            }
            nc.navigate(R.id.signInFragment, null, options)
        }
    }
}
