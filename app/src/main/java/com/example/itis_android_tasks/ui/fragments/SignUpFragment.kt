package com.example.itis_android_tasks.ui.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.itis_android_tasks.R
import com.example.itis_android_tasks.data.db.repositories.UserRepository
import com.example.itis_android_tasks.data.model.UserModel
import com.example.itis_android_tasks.databinding.FragmentSignUpBinding
import kotlinx.coroutines.launch

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            btnSubmit.setOnClickListener {
                val name = etName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val password = etPassword.text.toString().trim()

                val validationErrors = validateInput(name, email, phone, password)

                if (validationErrors.isNotEmpty()) {
                    displayErrors(validationErrors)
                    return@setOnClickListener
                }

                val user = UserModel(
                    name = name,
                    email = email,
                    phone = phone,
                    password = password,
                    deletedDate = null
                )

                lifecycleScope.launch {
                    if (!UserRepository.add(user)) {
                        etPhone.error = getString(R.string.phone_or_email_already_exists)
                    } else {
                        findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
                    }
                }
            }
        }
    }

    private fun validateInput(
        name: String,
        email: String,
        phone: String,
        password: String
    ): Map<View, String?> {
        return mapOf(
            binding.etName to getString(R.string.error_name_empty).takeIf { name.isEmpty() },
            binding.etEmail to when {
                email.isEmpty() -> getString(R.string.error_email_empty)
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> getString(R.string.error_invalid_email)
                else -> null
            },
            binding.etPhone to when {
                phone.isEmpty() -> getString(R.string.error_phone_empty)
                !Patterns.PHONE.matcher(phone).matches() -> getString(R.string.error_invalid_phone)
                else -> null
            },
            binding.etPassword to getString(R.string.error_password_empty).takeIf { password.isEmpty() }
        ).filterValues { it != null }
    }

    private fun displayErrors(errors: Map<View, String?>) {
        errors.forEach { (view, error) ->
            if (view is android.widget.EditText) {
                view.error = error
            }
        }
    }
}
