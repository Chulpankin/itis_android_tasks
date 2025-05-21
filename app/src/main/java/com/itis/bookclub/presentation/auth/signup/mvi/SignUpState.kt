package com.itis.bookclub.presentation.auth.signup.mvi

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isInvalidCredentials: Boolean = false,
    val isLoading: Boolean = false,
)