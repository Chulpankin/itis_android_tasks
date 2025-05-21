package com.itis.bookclub.presentation.auth.signin.combosable

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.itis.bookclub.presentation.auth.signin.SignInViewModel
import com.itis.bookclub.presentation.auth.signin.mvi.SignInAction

@Composable
internal fun ObserveActions(
    viewModel: SignInViewModel,
    snackbarHostState: SnackbarHostState,
) {
    LaunchedEffect(Unit) {
        viewModel.actionsFlow.collect { action ->
            when (action) {
                is SignInAction.ShowMessage ->
                    snackbarHostState.showSnackbar(message = action.message)
            }
        }
    }
}