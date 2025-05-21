package com.itis.bookclub.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itis.bookclub.R
import com.itis.bookclub.presentation.list.BookListFragment
import com.itis.bookclub.util.appComponent

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent.inject(activity = this)

        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                BookListFragment()
            )
            .commit()
    }
}