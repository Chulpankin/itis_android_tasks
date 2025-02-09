package com.example.itis_android_tasks.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itis_android_tasks.R
import com.example.itis_android_tasks.data.db.repositories.FilmRepository
import com.example.itis_android_tasks.data.db.repositories.UserRepository
import com.example.itis_android_tasks.data.model.FilmModel
import com.example.itis_android_tasks.databinding.FragmentFavoritesBinding
import com.example.itis_android_tasks.ui.adapter.FilmAdapter
import com.example.itis_android_tasks.utils.Keys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private var filmAdapter: FilmAdapter? = null
    private val sharedPreferences
            by lazy { requireContext().getSharedPreferences(Keys.PREFS_NAME, Context.MODE_PRIVATE) }
    private val userId: Int get() = sharedPreferences.getInt(Keys.KEY_USER_ID, -1)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        lifecycleScope.launch { showFavoriteFilms() }
    }

    private fun initRecyclerView() {
        filmAdapter = FilmAdapter(
            onItemClicked = ::navigateToFilmPage,
            onBtnClicked = ::onFavoriteBtnClicked
        )

        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.adapter = filmAdapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val filmModel = filmAdapter?.getItemAt(viewHolder.adapterPosition)
                filmModel?.let {
                    lifecycleScope.launch(Dispatchers.IO) {
                        FilmRepository.delete(filmModel.name)
                        UserRepository.deleteFavorite(userId, filmModel.name)
                        showFavoriteFilms()
                    }
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.rvFavorites)
    }

    private fun navigateToFilmPage(filmModel: FilmModel) {
        findNavController().navigate(
            R.id.action_favoritesFragment_to_filmPageFragment,
            bundleOf(Keys.KEY_FILM_NAME to filmModel.name)
        )
    }

    private fun onFavoriteBtnClicked(filmModel: FilmModel) {
        lifecycleScope.launch {
            UserRepository.deleteFavorite(userId, filmModel.name)
            showToast(getString(R.string.removed_from_favorites))
            showFavoriteFilms()
        }
    }

    private suspend fun showFavoriteFilms() {
        val favoriteFilms = UserRepository.getAllFavorites(userId)
        filmAdapter?.setItems(favoriteFilms)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        filmAdapter = null
    }
}
