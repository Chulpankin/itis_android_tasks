package com.itis.bookclub.presentation.auth.signin

import androidx.lifecycle.viewModelScope
import com.itis.bookclub.domain.usecase.IsUserAuthorizedUseCase
import com.itis.bookclub.domain.usecase.SignInUseCase
import com.itis.bookclub.presentation.auth.signin.mvi.SignInAction
import com.itis.bookclub.presentation.auth.signin.mvi.SignInState
import com.itis.bookclub.presentation.auth.signin.mvi.SignInEvent
import com.itis.bookclub.presentation.base.BaseViewModel
import com.itis.bookclub.presentation.utils.Navigator
import com.itis.bookclub.util.AppException
import com.itis.bookclub.util.AppExceptionHandler
import com.itis.bookclub.util.runSuspendCatching
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val navigator: Navigator,
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase,
    private val signInUseCase: SignInUseCase,
    private val appExceptionHandler: AppExceptionHandler
) : BaseViewModel<SignInState, SignInEvent, SignInAction>(
    initialState = SignInState()
) {

    init {
        checkUserAuthorized()
    }

    override fun obtainEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignInClick -> signIn()
            is SignInEvent.SignUpClick -> navigator.navigateToSignUp()
            is SignInEvent.EmailChanged -> updateEmail(event.email)
            is SignInEvent.PasswordChanged -> updatePassword(event.password)
        }
    }

    private fun checkUserAuthorized() {
        viewModelScope.launch {
            runSuspendCatching(appExceptionHandler) {
                isUserAuthorizedUseCase.invoke()
            }.onSuccess {
                if (it) navigator.navigateToMain()
            }
        }
    }

    private fun signIn() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            runSuspendCatching(appExceptionHandler) {
                signInUseCase.invoke(_uiState.value.email, _uiState.value.password)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(isLoading = false)
                navigator.navigateToMain()
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isInvalidCredentials = it is AppException.AuthInvalidCredentialsException
                )
                _actionsFlow.emit(SignInAction.ShowMessage(it.message.orEmpty()))
            }
        }
    }

    private fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email, isInvalidCredentials = false)
    }

    private fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password, isInvalidCredentials = false)
    }
}
