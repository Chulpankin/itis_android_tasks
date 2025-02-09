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
import com.example.itis_android_tasks.data.db.repositories.UserRepository
import com.example.itis_android_tasks.databinding.FragmentProfileBinding
import com.example.itis_android_tasks.utils.Keys
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val sharedPreferences
            by lazy { requireContext().getSharedPreferences(Keys.PREFS_NAME, Context.MODE_PRIVATE) }
    private val userId: Int get() = sharedPreferences.getInt(Keys.KEY_USER_ID, -1)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (userId == -1) {
            findNavController().navigate(R.id.action_profileFragment_to_logInFragment)
            return
        }

        loadUserData()
        setupListeners()
    }

    private fun loadUserData() {
        lifecycleScope.launch {
            val currentUser = UserRepository.getUserById(userId)
            binding.run {
                tvName.text = currentUser.name
                tvPhone.text = currentUser.email
                tvEmail.text = currentUser.phone

                etPhone.setText(currentUser.phone)
                etPassword.setText(currentUser.password)
            }
        }
    }

    private fun setupListeners() {
        binding.run {
            btnSubmit.setOnClickListener {
                updateUserInfo()
            }

            btnLogout.setOnClickListener {
                logoutUser()
            }

            btnDeleteAccount.setOnClickListener {
                deleteAccount()
            }
        }
    }

    private fun updateUserInfo() {
        lifecycleScope.launch {
            val currentUser = UserRepository.getUserById(userId)

            val phone = binding.etPhone.text.toString()
            val password = binding.etPassword.text.toString()

            if (phone.isNotEmpty()) {
                if (UserRepository.updatePhone(currentUser.userId, phone)) {
                    binding.tvPhone.text = phone
                    showToast(R.string.phone_number_changed)
                } else {
                    showToast(R.string.phone_number_already_in_use)
                }
            }

            if (password.isNotEmpty()) {
                UserRepository.updatePassword(currentUser.userId, password)
                showToast(R.string.password_changed)
            }
        }
    }

    private fun logoutUser() {
        with(sharedPreferences.edit()) {
            remove(Keys.KEY_USER_ID)
            apply()
        }
        findNavController().navigate(R.id.action_profileFragment_to_logInFragment)
    }

    private fun deleteAccount() {
        lifecycleScope.launch {
            with(sharedPreferences.edit()) {
                remove(Keys.KEY_USER_ID)
                apply()
            }
            findNavController().navigate(R.id.action_profileFragment_to_logInFragment)
        }
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(requireContext(), getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
