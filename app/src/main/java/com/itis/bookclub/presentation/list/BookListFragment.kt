package com.itis.bookclub.presentation.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.bookclub.R
import com.itis.bookclub.databinding.FragmentBookListBinding
import com.itis.bookclub.presentation.base.BaseFragment
import com.itis.bookclub.util.appComponent
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListFragment : BaseFragment() {

    @Inject
    lateinit var factory: Lazy<ViewModelProvider.Factory>

    private val viewModel: BookListViewModel by viewModels { factory.get() }

    private val viewBinding: FragmentBookListBinding by viewBinding(FragmentBookListBinding::bind)

    private var adapter: BookPagingAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_book_list
    }

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(fragment = this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BookPagingAdapter(onItemClicked = ::onBookClicked)
        viewBinding.rvBooks.adapter = adapter?.withLoadStateFooter(
            footer = PagingLoadStateAdapter { adapter?.retry() }
        )

        viewBinding.rvBooks.layoutManager = LinearLayoutManager(context)

        viewBinding.swipeRefresh.setOnRefreshListener {
            adapter?.refresh()
        }

        adapter?.addLoadStateListener { loadState ->
            val isRefreshing = loadState.source.refresh is LoadState.Loading
            viewBinding.swipeRefresh.isRefreshing = isRefreshing
        }

        viewBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.setQuery(it.trim()) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        observe()
    }

    private fun onBookClicked(bookId: String) {
        //TODO
    }

    private fun observe() {
        with(viewModel) {
            lifecycleScope.launch {
                books.collectLatest { pagingData ->
                    adapter?.submitData(pagingData)
                }
            }

            isLoading.observe {
                setLoadingVisibility(it)
            }

            isError.observe {
                setErrorVisibility(it)
            }
        }
    }


    private fun setLoadingVisibility(isVisible: Boolean) {
        viewBinding.loading.root.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setErrorVisibility(isVisible: Boolean) {
        viewBinding.error.root.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }


    companion object {
        fun newInstance() = BookListFragment()
    }
}
