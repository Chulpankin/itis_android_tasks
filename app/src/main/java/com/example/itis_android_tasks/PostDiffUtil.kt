package com.example.itis_android_tasks

import androidx.recyclerview.widget.DiffUtil

class PostDiffUtil(
    private val oldList: List<PostModel>,
    private val newList: List<PostModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.title == new.title &&
                old.description == new.description &&
                old.imageUrl == new.imageUrl
    }
}