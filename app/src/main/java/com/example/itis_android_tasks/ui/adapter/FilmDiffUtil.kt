package com.example.itis_android_tasks.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.itis_android_tasks.data.model.FilmModel

class FilmDiffUtil(
    private val oldItemsList: List<FilmModel>,
    private val newItemsList: List<FilmModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItemsList.size
    override fun getNewListSize(): Int = newItemsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemsList[oldItemPosition].name == newItemsList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemsList[oldItemPosition] == newItemsList[newItemPosition]
    }
}
