package com.example.itis_android_tasks

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val data: MutableList<QuestionModel> = mutableListOf()

    override fun getItemCount(): Int = data.size

    override fun createFragment(position: Int): Fragment =
        QuestionnaireFragment.newInstance(id = position + 1)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<QuestionModel>) {
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }
}