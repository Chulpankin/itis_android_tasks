package com.example.itis_android_tasks

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itis_android_tasks.databinding.ItemPostBinding

class PostViewHolder(
    private val binding: ItemPostBinding,
    private val onItemClicked: (PostModel) -> Unit,
    private val onItemLongPress: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root){

    fun bind(model: PostModel, pos: Int) {
        binding.tvTitle.text = model.title
        Glide.with(binding.root)
            .load(model.imageUrl)
            .into(binding.ivImage)

        binding.root.setOnClickListener { onItemClicked(model) }

        binding.root.setOnLongClickListener {
            onItemLongPress(pos)
            true
        }
    }

}