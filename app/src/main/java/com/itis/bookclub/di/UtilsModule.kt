package com.itis.bookclub.di

import android.content.Context
import com.itis.bookclub.util.Toaster
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object UtilsModule {

    @Provides
    @Singleton
    fun provideToaster(context: Context): Toaster {
        return Toaster(context)
    }
}
