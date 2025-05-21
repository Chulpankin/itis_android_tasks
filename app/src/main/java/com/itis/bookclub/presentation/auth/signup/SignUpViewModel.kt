package com.itis.bookclub.presentation.auth.signup


import androidx.lifecycle.viewModelScope
import com.itis.bookclub.domain.usecase.IsUserAuthorizedUseCase
import com.itis.bookclub.domain.usecase.SignUpUseCase
import com.itis.bookclub.presentation.auth.signup.mvi.SignUpAction
import com.itis.bookclub.presentation.auth.signup.mvi.SignUpEvent
import com.itis.bookclub.presentation.auth.signup.mvi.SignUpState
import com.itis.bookclub.presentation.base.BaseViewModel
import com.itis.bookclub.presentation.utils.Navigator
import com.itis.bookclub.util.AppException
import com.itis.bookclub.util.AppExceptionHandler
import com.itis.bookclub.util.runSuspendCatching
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val navigator: Navigator,
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val appExceptionHandler: AppExceptionHandler
) : BaseViewModel<SignUpState, SignUpEvent, SignUpAction>(
    initialState = SignUpState()
) {

    init {
        checkUserAuthorized()
    }

    override fun obtainEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignInClick -> navigator.navigateToSignIn()
            is SignUpEvent.SignUpClick -> signUp()
            is SignUpEvent.NameChanged -> updateName(event.name)
            is SignUpEvent.EmailChanged -> updateEmail(event.email)
            is SignUpEvent.PasswordChanged -> updatePassword(event.password)
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

    private fun signUp() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            runSuspendCatching(appExceptionHandler) {
                signUpUseCase.invoke(_uiState.value.name, _uiState.value.email, _uiState.value.password)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(isLoading = false)
                navigator.navigateToMain()
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isInvalidCredentials = it is AppException.AuthInvalidCredentialsException
                )
                _actionsFlow.emit(SignUpAction.ShowMessage(it.message.orEmpty()))
            }
        }
    }

    private fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name, isInvalidCredentials = false)
    }

    private fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email, isInvalidCredentials = false)
    }

    private fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password, isInvalidCredentials = false)
    }
}
