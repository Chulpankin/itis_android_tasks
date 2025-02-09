package com.example.itis_android_tasks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.itis_android_tasks.data.model.FilmModel
import com.example.itis_android_tasks.databinding.ItemFilmBinding
import com.example.itis_android_tasks.ui.holders.FilmViewHolder

class FilmAdapter(
    private val onItemClicked: (FilmModel) -> Unit,
    private val onBtnClicked: (FilmModel) -> Unit
) : RecyclerView.Adapter<FilmViewHolder>() {

    private var itemsList = mutableListOf<FilmModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            viewBinding = ItemFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked = onItemClicked,
            onBtnClicked = onBtnClicked
        )
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bindItem(itemsList[position])
    }

    override fun getItemCount(): Int = itemsList.size

    fun setItems(newList: List<FilmModel>) {
        val diffCallback = FilmDiffUtil(itemsList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        itemsList.clear()
        itemsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateItem(position: Int, item: FilmModel) {
        itemsList[position] = item
        notifyItemChanged(position, item.isFavorite)
    }

    fun getItemAt(position: Int): FilmModel? = itemsList.getOrNull(position)
}
