package com.itis.bookclub.presentation

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.itis.bookclub.util.observe
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    inline fun <T> Flow<T>.observe(crossinline block: (T) -> Unit): Job {
        return observe(fragmentLifecycleOwner = this@BaseFragment.viewLifecycleOwner, block)
    }
}