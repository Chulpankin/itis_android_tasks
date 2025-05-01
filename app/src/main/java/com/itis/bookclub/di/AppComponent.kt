package com.itis.bookclub.di

import android.content.Context
import com.itis.bookclub.data.di.DataModule
import com.itis.bookclub.data.di.NetworkModule
import com.itis.bookclub.domain.di.DomainModule
import com.itis.bookclub.presentation.MainActivity
import com.itis.bookclub.presentation.details.BookDetailsFragment
import com.itis.bookclub.presentation.details.BookDetailsViewModel
import com.itis.bookclub.presentation.di.PresentationModule
import com.itis.bookclub.presentation.list.BookListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        NetworkModule::class,
        DomainModule::class,
        PresentationModule::class,
        ViewModelModule::class,
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
    fun inject(fragment: BookListFragment)
    fun inject(fragment: BookDetailsFragment)

    fun bookDetailsViewModel(): BookDetailsViewModel.Factory
}