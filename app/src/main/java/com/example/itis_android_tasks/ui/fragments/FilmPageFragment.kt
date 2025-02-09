package com.example.itis_android_tasks.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.itis_android_tasks.R
import com.example.itis_android_tasks.data.db.entities.RateEntity
import com.example.itis_android_tasks.data.db.repositories.FilmRepository
import com.example.itis_android_tasks.data.db.repositories.RateRepository
import com.example.itis_android_tasks.databinding.FragmentFilmPageBinding
import com.example.itis_android_tasks.utils.Keys
import kotlinx.coroutines.launch

class FilmPageFragment : Fragment(R.layout.fragment_film_page) {

    private var _binding: FragmentFilmPageBinding? = null
    private val binding get() = _binding!!
    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences(Keys.PREFS_NAME, Context.MODE_PRIVATE)
    }
    private val userId: Int get() = sharedPreferences.getInt(Keys.KEY_USER_ID, -1)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmPageBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filmName = requireArguments().getString(Keys.KEY_FILM_NAME)

        if (filmName != null) {
            lifecycleScope.launch {
                val film = FilmRepository.getEntityByName(filmName)
                binding.run {
                    tvFilmName.text = film.name
                    tvYear.text = film.year.toString()
                    tvDesc.text = film.description
                    setAvgRate(film.filmId, tvAvgRating)
                    setupRatingControls(film.filmId)
                }
            }
        } else {
            parentFragmentManager.popBackStack()
        }
    }

    private suspend fun setAvgRate(filmId: Int, tvAvgRating: TextView) {
        tvAvgRating.text = RateRepository.getAvg(filmId).toString()
    }

    private suspend fun setupRatingControls(filmId: Int) {
        val currentRate = RateRepository.get(filmId, userId)
        binding.run {
            if (currentRate != null) {
                seekbarRate.progress = currentRate.rating
                seekbarRate.isEnabled = false
                tvSbValue.text = currentRate.rating.toString()
                btnSubmit.isEnabled = false
            } else {
                seekbarRate.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        tvSbValue.text = progress.toString()
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                })

                btnSubmit.setOnClickListener {
                    lifecycleScope.launch {
                        RateRepository.add(
                            RateEntity(
                                filmId = filmId,
                                rating = seekbarRate.progress,
                                userId = userId
                            )
                        )
                        setAvgRate(filmId, tvAvgRating)
                    }
                    seekbarRate.isEnabled = false
                    btnSubmit.isEnabled = false
                }
            }
        }
    }
}
