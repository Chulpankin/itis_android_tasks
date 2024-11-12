package com.example.itis_android_tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.itis_android_tasks.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_first) {
    private var _viewBinding: FragmentSecondBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentSecondBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text = arguments?.getString(TEXT)
        viewBinding.textViewSecond.text =
            if (text.isNullOrEmpty()) getString(R.string.text)
            else text

        viewBinding.buttonToThirdFromSecond.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    ThirdFragment.newInstance(text = viewBinding.textViewSecond.text.toString()),
                    ThirdFragment.THIRD_FRAGMENT_TAG
                )
                .addToBackStack(null)
                .commit()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _viewBinding = null
    }

    companion object {
        const val TEXT = "text"
        const val SECOND_FRAGMENT_TAG = "SECOND_FRAGMENT_TAG"

        fun newInstance(text: String): SecondFragment =
            SecondFragment().apply {
                arguments = bundleOf(TEXT to text)
            }

    }
}