package com.itis.bookclub.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.itis.bookclub.domain.usecase.GetBooksUseCase
import com.itis.bookclub.domain.utils.RemoteConfigService
import com.itis.bookclub.presentation.model.BookUiModel
import com.itis.bookclub.presentation.utils.toUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class BookListViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val remoteConfigService: RemoteConfigService,
) : ViewModel() {

    private val queryFlow = MutableStateFlow(DEFAULT_QUERY)

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _isDetailsEnabled = MutableStateFlow(false)
    val isDetailsEnabled = _isDetailsEnabled.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val books: StateFlow<PagingData<BookUiModel>> = queryFlow
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getBooksUseCase(query).map { pagingData -> pagingData.map { it.toUiModel() } }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PagingData.empty()
        )

    fun setQuery(query: String) {
        queryFlow.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "dictionary"
    }
}
