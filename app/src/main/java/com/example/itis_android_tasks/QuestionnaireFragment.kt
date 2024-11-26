package com.example.itis_android_tasks

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.itis_android_tasks.databinding.FragmentQuestionnaireBinding

class QuestionnaireFragment : Fragment(R.layout.fragment_questionnaire) {

    private val viewBinding: FragmentQuestionnaireBinding by viewBinding(FragmentQuestionnaireBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt(ID) ?: -1

        val model = QuestionRepository.getQuestionById(id)

        model?.answers?.forEach { answer ->
            val radioButton = RadioButton(requireContext())
            radioButton.text = answer
            radioButton.layoutParams = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
            )
            radioButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    radioButton.setBackgroundColor(Color.GREEN)
                } else {
                    radioButton.setBackgroundColor(Color.TRANSPARENT)
                }
            }
            viewBinding.answerGroup.addView(radioButton)

            viewBinding.counterTv.text =
                getString(R.string.counter, id.toString(), QuestionRepository.getSize().toString())

            viewBinding.questionTv.text = model.question

            viewBinding.buttonPrevious.isEnabled = id != 1

            viewBinding.buttonPrevious.setOnClickListener {
                requireActivity()
                    .findViewById<ViewPager2>(R.id.content_vp)
                    .setCurrentItem(id - 2, true)
            }

            viewBinding.buttonNext.setOnClickListener {
                requireActivity()
                    .findViewById<ViewPager2>(R.id.content_vp)
                    .setCurrentItem(id, true)

            }

            if (id == QuestionRepository.getSize()) {
                viewBinding.buttonNext.text = getString(R.string.close)
                viewBinding.buttonNext.setOnClickListener {
                    Toast.makeText(context,
                        getString(R.string.questionnaire_ended), Toast.LENGTH_SHORT).show()
                }
                viewBinding.buttonNext.setBackgroundColor(Color.GREEN)
            }
        }

    }

    companion object {
        const val ID = "id"

        fun newInstance(id: Int): QuestionnaireFragment =
            QuestionnaireFragment().apply {
                arguments = bundleOf(ID to id)
            }

    }
}