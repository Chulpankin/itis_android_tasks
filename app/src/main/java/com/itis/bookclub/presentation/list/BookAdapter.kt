package com.itis.bookclub.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.bookclub.databinding.ItemBookBinding
import com.itis.bookclub.presentation.model.BookUiModel

class BookAdapter(
    private val onItemClicked: (String) -> Unit,
) : RecyclerView.Adapter<BookViewHolder>() {

    private var booksList = listOf<BookUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            viewBinding = ItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked = onItemClicked
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bindItem(booksList[position])
    }

    override fun getItemCount(): Int = booksList.size

    fun setList(list: List<BookUiModel>) {
        booksList = list
    }
}