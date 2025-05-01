package com.itis.bookclub.data.di

import com.itis.bookclub.data.repository.BookRepositoryImpl
import com.itis.bookclub.domain.repository.BookRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindBookRepository(impl: BookRepositoryImpl): BookRepository
}