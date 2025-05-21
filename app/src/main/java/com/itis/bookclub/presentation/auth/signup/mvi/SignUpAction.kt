package com.itis.bookclub.presentation.auth.signup.mvi

sealed interface SignUpAction {
    data class ShowMessage(val message: String) : SignUpAction
}