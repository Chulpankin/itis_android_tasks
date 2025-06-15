package com.itis.bookclub.di

import android.content.Context
import com.itis.bookclub.data.di.DataModule
import com.itis.bookclub.data.di.FirebaseModule
import com.itis.bookclub.data.di.LocalModule
import com.itis.bookclub.data.di.NetworkModule
import com.itis.bookclub.domain.di.DomainModule
import com.itis.bookclub.presentation.MainActivity
import com.itis.bookclub.presentation.auth.signin.SignInFragment
import com.itis.bookclub.presentation.auth.signup.SignUpFragment
import com.itis.bookclub.presentation.details.BookDetailsFragment
import com.itis.bookclub.presentation.details.BookDetailsViewModel
import com.itis.bookclub.presentation.di.PresentationModule
import com.itis.bookclub.presentation.list.BookListFragment
import com.itis.bookclub.presentation.service.BookClubFirebaseMessagingService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        NetworkModule::class,
        LocalModule::class,
        DomainModule::class,
        PresentationModule::class,
        ViewModelModule::class,
        UtilsModule::class,
        FirebaseModule::class,
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun provideContext(ctx: Context): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(service: BookClubFirebaseMessagingService)
    fun inject(fragment: BookListFragment)
    fun inject(fragment: BookDetailsFragment)
    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFragment)

    fun bookDetailsViewModel(): BookDetailsViewModel.Factory
}