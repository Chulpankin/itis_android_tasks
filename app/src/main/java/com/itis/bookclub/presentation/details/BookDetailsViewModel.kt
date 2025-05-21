package com.itis.bookclub.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.itis.bookclub.domain.usecase.GetBookDetailsUseCase
import com.itis.bookclub.presentation.model.BookDetailsUiModel
import com.itis.bookclub.presentation.utils.toUiModel
import com.itis.bookclub.util.AppExceptionHandler
import com.itis.bookclub.util.runSuspendCatching
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailsViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val exceptionHandler: AppExceptionHandler,
) : ViewModel() {

    private val _book = MutableStateFlow<BookDetailsUiModel?>(null)
    val book = _book.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    init { loadDetails() }

    private fun loadDetails() {
        viewModelScope.launch {
            _book.value = null
            _isLoading.value = true
            _isError.value = false

            runSuspendCatching(exceptionHandler) {
                getBookDetailsUseCase.invoke(id = id)
            }.onSuccess {
                _book.value = it.toUiModel()
                _isLoading.value = false
            }.onFailure {
                Log.e("BookDetailsViewModel", it.message.toString())
                _isError.value = true
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted id: String): BookDetailsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            id: String,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }
}