package com.itis.bookclub.di

import androidx.lifecycle.ViewModelProvider
import com.itis.bookclub.util.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindDaggerFactoryToViewModelFactory(impl: DaggerViewModelFactory): ViewModelProvider.Factory

}