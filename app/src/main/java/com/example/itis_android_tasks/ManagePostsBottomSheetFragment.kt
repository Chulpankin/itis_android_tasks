package com.example.itis_android_tasks

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.itis_android_tasks.databinding.FragmentManagePostsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ManagePostsBottomSheetFragment : BottomSheetDialogFragment(
    R.layout.fragment_manage_posts_bottom_sheet
) {
    private val viewBinding: FragmentManagePostsBottomSheetBinding
    by viewBinding(FragmentManagePostsBottomSheetBinding::bind)

    private var updateDataListener: UpdateDataListener? = null

    fun setUpdateDataListener(updateDataListener: UpdateDataListener) {
        this.updateDataListener = updateDataListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var number = 0
        viewBinding.editTextNumber.addTextChangedListener { input ->
            number = input.toString().toIntOrNull() ?: 0
        }

        viewBinding.buttonAddRandom.setOnClickListener {
            PostRepository.addRandomItems(number)
            updatePosts()
        }

        viewBinding.buttonRemoveRandom.setOnClickListener {
            PostRepository.removeRandomItems(number)
            updatePosts()
        }

        viewBinding.buttonAddOneRandom.setOnClickListener {
            PostRepository.addOneRandomItem()
            updatePosts()
        }

        viewBinding.buttonRemoveOneRandom.setOnClickListener {
            PostRepository.removeOneRandomItem()
            updatePosts()
        }
    }

    private fun updatePosts() {
        updateDataListener?.updateData()
    }
}