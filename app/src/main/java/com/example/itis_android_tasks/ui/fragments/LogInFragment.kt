package com.example.itis_android_tasks.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.itis_android_tasks.R
import com.example.itis_android_tasks.data.db.entities.UserEntity
import com.example.itis_android_tasks.data.db.repositories.UserRepository
import com.example.itis_android_tasks.databinding.FragmentLogInBinding
import com.example.itis_android_tasks.utils.Keys
import kotlinx.coroutines.launch

class LogInFragment : Fragment(R.layout.fragment_log_in) {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
            }

            btnSubmit.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    handleAuthorization(email, password)
                } else {
                    showToast(getString(R.string.please_enter_credentials))
                }
            }
        }
    }

    private fun handleAuthorization(email: String, password: String) {
        lifecycleScope.launch {
            val user = UserRepository.getUserByData(email, password)

            saveUserToPreferences(user)
            findNavController().navigate(R.id.action_logInFragment_to_filmsFragment)
        }
    }

    private fun saveUserToPreferences(user: UserEntity) {
        val sharedPreferences = requireActivity().getSharedPreferences(Keys.PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(Keys.KEY_USER_EMAIL, user.email)
            putString(Keys.KEY_USER_NAME, user.name)
            putInt(Keys.KEY_USER_ID, user.userId)
            apply()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
