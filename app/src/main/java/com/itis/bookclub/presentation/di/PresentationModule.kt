package com.itis.bookclub.presentation.di

import androidx.lifecycle.ViewModel
import com.itis.bookclub.di.keys.ViewModelKey
import com.itis.bookclub.presentation.auth.signin.SignInViewModel
import com.itis.bookclub.presentation.auth.signup.SignUpViewModel
import com.itis.bookclub.presentation.list.BookListViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class PresentationModule {

    @[Provides IntoMap ViewModelKey(BookListViewModel::class)]
    fun provideBookListViewModel(viewModel: BookListViewModel): ViewModel = viewModel

    @[Provides IntoMap ViewModelKey(SignInViewModel::class)]
    fun provideSignInViewModel(viewModel: SignInViewModel): ViewModel = viewModel

    @[Provides IntoMap ViewModelKey(SignUpViewModel::class)]
    fun provideSignUpViewModel(viewModel: SignUpViewModel): ViewModel = viewModel
}