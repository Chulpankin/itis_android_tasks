package com.example.itis_android_tasks

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.itis_android_tasks.databinding.FragmentMainPageBinding

class MainPageFragment : Fragment(R.layout.fragment_main_page), UpdateDataListener {

    private val viewBinding: FragmentMainPageBinding by viewBinding(FragmentMainPageBinding::bind)

    private var postAdapter: PostAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postAdapter = PostAdapter(
            onItemClicked = ::onItemClicked,
            firstButtonClicked = ::firstButtonClicked,
            secondButtonCLicked = ::secondButtonClicked,
            thirdButtonClicked = ::thirdButtonClicked,
            onItemLongPress = ::onItemLongPress
        )
       updateData()

        viewBinding.recyclerView.adapter = postAdapter

        viewBinding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(postAdapter!!))
        itemTouchHelper.attachToRecyclerView(viewBinding.recyclerView)


        viewBinding.fabBottomDialog.setOnClickListener {
            val managePostsBottomSheetFragment = ManagePostsBottomSheetFragment()
            managePostsBottomSheetFragment.setUpdateDataListener(updateDataListener = this)
            managePostsBottomSheetFragment.show(parentFragmentManager, null)
        }
    }


    override fun updateData() {
        Log.d("MainPage", "setting new list")
        postAdapter?.setData(PostRepository.getPosts())
    }


    private fun onItemClicked(model: PostModel) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                DetailsFragment.newInstance(model.id),
                DetailsFragment.DETAILS_FRAGMENT_TAG
            )
            .addToBackStack(null)
            .commit()
    }

    private fun onItemLongPress(position: Int) {
        showDeleteConfirmationDialog(position)
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление элемента")
            .setMessage("Вы уверены, что хотите удалить этот элемент?")
            .setPositiveButton("Да") { dialog, _ ->
                PostRepository.removeItem(position)
                updateData()
                dialog.dismiss()
            }
            .setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun firstButtonClicked() {
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun secondButtonClicked() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 3 else 1
            }
        }
        viewBinding.recyclerView.layoutManager = gridLayoutManager
        postAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun thirdButtonClicked() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position % 4) {
                    0, 3 -> 2
                    else -> 1
                }
            }
        }
        viewBinding.recyclerView.layoutManager = gridLayoutManager
        postAdapter?.notifyDataSetChanged()
    }
}