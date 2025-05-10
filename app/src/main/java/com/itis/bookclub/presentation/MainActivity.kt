package com.itis.bookclub.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.itis.bookclub.R
import com.itis.bookclub.presentation.list.BookListFragment
import com.itis.bookclub.util.appComponent

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        appComponent.inject(activity = this)

        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                BookListFragment.newInstance()
            )
            .commit()
    }
}