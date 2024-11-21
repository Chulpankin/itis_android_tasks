package com.example.itis_android_tasks

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.itis_android_tasks.databinding.ItemButtonsBinding
import com.example.itis_android_tasks.databinding.ItemPostBinding

class PostAdapter(
    private val firstButtonClicked: () -> Unit,
    private val secondButtonCLicked: () -> Unit,
    private val thirdButtonClicked: () -> Unit,
    private val onItemClicked: (PostModel) -> Unit,
    private val onItemLongPress: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: MutableList<PostModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_buttons ->
                ButtonsViewHolder(
                    binding = ItemButtonsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    firstButtonClicked = firstButtonClicked,
                    secondButtonCLicked = secondButtonCLicked,
                    thirdButtonClicked = thirdButtonClicked
                )

            R.layout.item_post ->
                PostViewHolder(
                    binding = ItemPostBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClicked = onItemClicked,
                    onItemLongPress = onItemLongPress
                )

            else -> throw IllegalStateException("Unknown holder")
        }

    override fun getItemCount(): Int = data.size + 1

    fun setData(newList: List<PostModel>) {
        val diffCallback = PostDiffUtil(
            oldList = data,
            newList = newList
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newList)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) (holder as? ButtonsViewHolder)?.bind()
        else (holder as? PostViewHolder)?.bind(data[position - 1], position - 1)
    }

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> R.layout.item_buttons
            else -> R.layout.item_post
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyDataSetChanged()
    }
}