package com.example.itis_android_tasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.itis_android_tasks.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {
    private var _viewBinding: FragmentFirstBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentFirstBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewBinding.buttonToSecond.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    SecondFragment.newInstance(text = viewBinding.editText.text.toString()),
                    SecondFragment.SECOND_FRAGMENT_TAG
                )
                .addToBackStack(null)
                .commit()
        }

        viewBinding.buttonToThird.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    SecondFragment.newInstance(text = viewBinding.editText.text.toString()),
                    SecondFragment.SECOND_FRAGMENT_TAG
                )
                .addToBackStack(null)
                .commit()


            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    ThirdFragment.newInstance(text = viewBinding.editText.text.toString()),
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
        const val FIRST_FRAGMENT_TAG = "FIRST_FRAGMENT_TAG"
    }
}