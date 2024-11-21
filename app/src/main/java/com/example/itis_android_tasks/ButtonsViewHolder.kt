package com.example.itis_android_tasks

import androidx.recyclerview.widget.RecyclerView
import com.example.itis_android_tasks.databinding.ItemButtonsBinding

class ButtonsViewHolder(
    private val binding: ItemButtonsBinding,
    private val firstButtonClicked: () -> Unit,
    private val secondButtonCLicked: () -> Unit,
    private val thirdButtonClicked: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {


    fun bind() {
        binding.buttonFirst.setOnClickListener {
            firstButtonClicked()
        }

        binding.buttonSecond.setOnClickListener {
            secondButtonCLicked()
        }

        binding.buttonThird.setOnClickListener {
            thirdButtonClicked()
        }
    }
}