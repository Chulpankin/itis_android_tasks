package com.itis.bookclub.util

import android.content.Context
import android.widget.Toast
import com.itis.bookclub.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Toaster @Inject constructor(
    private val context: Context
) {
    private fun show(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showFromApi() {
        Toast.makeText(context, context.getString(R.string.loaded_from_api), Toast.LENGTH_SHORT).show()
    }

    fun showFromCache() {
        Toast.makeText(context, context.getString(R.string.loaded_from_cache), Toast.LENGTH_SHORT).show()
    }
}
