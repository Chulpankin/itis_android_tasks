package com.itis.bookclub.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itis.bookclub.domain.usecase.GetBooksUseCase
import com.itis.bookclub.presentation.model.BookUiModel
import com.itis.bookclub.presentation.utils.toUiModel
import com.itis.bookclub.util.AppExceptionHandler
import com.itis.bookclub.util.runSuspendCatching
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val exceptionHandler: AppExceptionHandler,
) : ViewModel() {

    private val _booksList = MutableStateFlow<List<BookUiModel>>(emptyList())
    val booksList = _booksList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    init {
        loadList()
    }

    private fun loadList() {
        viewModelScope.launch {
            _booksList.value = emptyList()
            _isLoading.value = true
            _isError.value = false

            runSuspendCatching(exceptionHandler) {
                //example data
                getBooksUseCase.invoke(query = "dictionary", page = 1)
            }.onSuccess {
                _booksList.value = it.map { domainModel -> domainModel.toUiModel() }
            }.onFailure {
                _isError.value = true
            }
        }
    }
}