package com.example.itis_android_tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.itis_android_tasks.SecondFragment.Companion.TEXT
import com.example.itis_android_tasks.databinding.FragmentFirstBinding
import com.example.itis_android_tasks.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third) {
    private var _viewBinding: FragmentThirdBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentThirdBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text = arguments?.getString(TEXT)
        viewBinding.textViewThird.text =
            if (text.isNullOrEmpty()) getString(R.string.text)
            else text
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _viewBinding = null
    }

    companion object {
        const val THIRD_FRAGMENT_TAG = "THIRD_FRAGMENT_TAG"

        fun newInstance(text: String): ThirdFragment =
            ThirdFragment().apply {
                arguments = bundleOf(TEXT to text)
            }
    }
}