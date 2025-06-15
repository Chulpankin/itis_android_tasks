package com.itis.bookclub.presentation.auth.signin.composable

import androidx.compose.runtime.Composable
import com.itis.bookclub.presentation.auth.composable.LoadingScreen
import com.itis.bookclub.presentation.auth.signin.mvi.SignInState

@Composable
internal fun ObserveState(
    uiState: SignInState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    SignInContent(
        email = uiState.email,
        password = uiState.password,
        isInvalidCredentials = uiState.isInvalidCredentials,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onSignUpClick = onSignUpClick,
        onSignInClick = onSignInClick)

    LoadingScreen(isLoading = uiState.isLoading)
}
