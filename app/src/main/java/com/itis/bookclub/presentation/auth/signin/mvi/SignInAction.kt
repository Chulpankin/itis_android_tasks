package com.itis.bookclub.presentation.auth.signin.mvi

sealed interface SignInAction {
    data class ShowMessage(val message: String) : SignInAction
}