package com.example.itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.itis_android_tasks.R
import com.example.itis_android_tasks.data.db.repositories.FilmRepository
import com.example.itis_android_tasks.data.model.FilmModel
import com.example.itis_android_tasks.databinding.FragmentAddFilmBinding
import com.example.itis_android_tasks.utils.Keys
import kotlinx.coroutines.launch

class AddFilmFragment : Fragment(R.layout.fragment_add_film) {

    private var _binding: FragmentAddFilmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFilmBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener { addFilm() }
    }

    private fun addFilm() {
        val filmName = binding.etFilmName.text.toString().trim()
        val year = binding.etYear.text.toString().trim()
        val description = binding.etDesc.text.toString().trim()

        if (filmName.isEmpty() || year.isEmpty() || description.isEmpty()) {
            showToast(getString(R.string.fill_all_fields))
            return
        }

        lifecycleScope.launch {
            if (FilmRepository.checkNameExists(filmName)) {
                showToast(getString(R.string.film_with_this_name_already_exists))
                clearFields()
            } else {
                val film = FilmModel(
                    name = filmName,
                    year = year.toInt(),
                    description = description
                )

                if (FilmRepository.add(film)) {
                    showToast(getString(R.string.film_added))
                    navigateToFilmPage(filmName)
                }
            }
        }
    }

    private fun navigateToFilmPage(filmName: String) {
        findNavController().navigate(
            R.id.action_addFilmFragment_to_filmPageFragment,
            Bundle().apply { putString(Keys.KEY_FILM_NAME, filmName) }
        )
    }

    private fun clearFields() {
        binding.etFilmName.text?.clear()
        binding.etYear.text?.clear()
        binding.etDesc.text?.clear()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
