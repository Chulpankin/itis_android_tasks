package com.itis.bookclub.presentation.auth.signin.mvi

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isInvalidCredentials: Boolean = false,
    val isLoading: Boolean = false,
)