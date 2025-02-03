package com.example.itis_android_tasks.model

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

enum class AppDispatchers(val dispatcher: CoroutineDispatcher) {
    DEFAULT(Dispatchers.Default),
    IO(Dispatchers.IO),
    MAIN(Dispatchers.Main),
    UNCONFINED(Dispatchers.Unconfined)
}