package com.itis.bookclub.presentation.auth.signup.composable


import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.itis.bookclub.presentation.auth.signup.SignUpViewModel
import com.itis.bookclub.presentation.auth.signup.mvi.SignUpAction

@Composable
internal fun ObserveActions(
    viewModel: SignUpViewModel,
    snackbarHostState: SnackbarHostState,
) {
    LaunchedEffect(Unit) {
        viewModel.actionsFlow.collect { action ->
            when (action) {
                is SignUpAction.ShowMessage ->
                    snackbarHostState.showSnackbar(message = action.message)
            }
        }
    }
}