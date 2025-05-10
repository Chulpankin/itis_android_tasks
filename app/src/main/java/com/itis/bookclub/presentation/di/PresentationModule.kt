package com.itis.bookclub.presentation.di

import androidx.lifecycle.ViewModel
import com.itis.bookclub.di.keys.ViewModelKey
import com.itis.bookclub.presentation.list.BookListViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class PresentationModule {

    @[Provides IntoMap ViewModelKey(BookListViewModel::class)]
    fun provideWeatherInfoViewModel(viewModel: BookListViewModel): ViewModel = viewModel
}