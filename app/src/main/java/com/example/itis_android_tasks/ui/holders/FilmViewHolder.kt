package com.example.itis_android_tasks.ui.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.itis_android_tasks.R
import com.example.itis_android_tasks.data.model.FilmModel
import com.example.itis_android_tasks.databinding.ItemFilmBinding

class FilmViewHolder(
    private val viewBinding: ItemFilmBinding,
    private val onItemClicked: (FilmModel) -> Unit,
    private val onBtnClicked: (FilmModel) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var item: FilmModel? = null

    init {
        viewBinding.root.setOnClickListener {
            item?.let(onItemClicked)
        }
        viewBinding.ivAddToFavorite.setOnClickListener {
            item?.let {
                val data = it.copy(isFavorite = !it.isFavorite)
                onBtnClicked(data)
            }
        }
    }

    fun bindItem(item: FilmModel) {
        this.item = item
        viewBinding.tvTitle.text = item.name
        viewBinding.tvYear.text = item.year.toString()
        changeBtnFavoriteStatus(item.isFavorite)
    }

    private fun changeBtnFavoriteStatus(isChecked: Boolean) {
        viewBinding.ivAddToFavorite.setImageResource(
            if (isChecked) R.drawable.baseline_star_24 else R.drawable.baseline_star_outline_24
        )
    }
}
