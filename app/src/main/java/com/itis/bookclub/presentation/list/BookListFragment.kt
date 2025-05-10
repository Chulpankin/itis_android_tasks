package com.itis.bookclub.presentation.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.bookclub.R
import com.itis.bookclub.databinding.FragmentBookListBinding
import com.itis.bookclub.presentation.BaseFragment
import com.itis.bookclub.presentation.details.BookDetailsFragment
import com.itis.bookclub.util.appComponent
import com.itis.bookclub.util.observe
import javax.inject.Inject
import dagger.Lazy

class BookListFragment : BaseFragment(R.layout.fragment_book_list) {

    @Inject
    lateinit var factory: Lazy<ViewModelProvider.Factory>

    private val viewModel: BookListViewModel by viewModels {
        factory.get()
    }

    private val viewBinding: FragmentBookListBinding
        by viewBinding(FragmentBookListBinding::bind)

    private var adapter: BookAdapter? = null

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(fragment = this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = viewBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        viewBinding.rvBooks.layoutManager = LinearLayoutManager(context)

        adapter = BookAdapter(onItemClicked = ::onBookClicked)
        viewBinding.rvBooks.adapter = adapter
    }

    private fun onBookClicked(bookId: String) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                BookDetailsFragment.newInstance(bookId)
            )
            .addToBackStack(null)
            .commit()
    }

    private fun setErrorVisibility(isVisible: Boolean) {
        viewBinding.error.root.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setLoadingVisibility(isVisible: Boolean) {
        viewBinding.loading.root.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun observe() {
        with(viewModel) {
            setErrorVisibility(isVisible = false)
            setLoadingVisibility(isVisible = false)
            booksList.observe(fragmentLifecycleOwner = this@BookListFragment) {
                adapter?.setList(it)
            }

            isLoading.observe {
                setErrorVisibility(isVisible = false)
                setLoadingVisibility(isVisible = it)
            }

            isError.observe {
                setErrorVisibility(isVisible = it)
                setLoadingVisibility(isVisible = false)
            }
        }
    }

    companion object {
        fun newInstance() = BookListFragment()
    }
}