package com.example.itis_android_tasks

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.itis_android_tasks.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewBinding: FragmentDetailsBinding by viewBinding(FragmentDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong(ID) ?: -1

        val model = PostRepository.getPostById(id)

        Glide.with(viewBinding.root)
            .load(model?.imageUrl)
            .into(viewBinding.ivImage)

        viewBinding.tvTitle.text = model?.title
        viewBinding.tvDesc.text = model?.description

    }

    companion object {
        const val ID = "id"
        const val DETAILS_FRAGMENT_TAG = "DETAILS_FRAGMENT_TAG"

        fun newInstance(id: Long): DetailsFragment =
            DetailsFragment().apply {
                arguments = bundleOf(ID to id)
            }

    }
}