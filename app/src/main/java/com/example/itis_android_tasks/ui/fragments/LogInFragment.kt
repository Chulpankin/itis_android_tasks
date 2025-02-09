package com.example.itis_android_tasks.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.itis_android_tasks.R
import com.example.itis_android_tasks.data.CurrentUser
import com.example.itis_android_tasks.data.db.entities.UserEntity
import com.example.itis_android_tasks.data.db.repositories.UserRepository
import com.example.itis_android_tasks.databinding.FragmentAuthorizationBinding
import com.example.itis_android_tasks.utils.Keys
import kotlinx.coroutines.launch

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    private val viewBinding by viewBinding(FragmentAuthorizationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.ac)
            }

            btnSubmit.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    handleAuthorization(email, password)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.please_enter_credentials),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun handleAuthorization(email: String, password: String) {
        lifecycleScope.launch {
            if (UserRepository.checkUserData(email, password)) {
                val user = UserRepository.getUserByData(email, password)
                saveUserToPreferences(user)
                CurrentUser.set(user.userId)
                findNavController().navigate(R.id.action_authorizationFragment_to_filmsFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.wrong_user_data),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun saveUserToPreferences(user: UserEntity) {
        val sharedPreferences = requireActivity().getSharedPreferences(
            Keys.PREFS_NAME,
            Context.MODE_PRIVATE
        )
        with(sharedPreferences.edit()) {
            putString(Keys.KEY_USER_EMAIL, user.email)
            putString(Keys.KEY_USER_NAME, user.name)
            putInt(Keys.KEY_USER_ID, user.userId)
            apply()
        }
    }
}
