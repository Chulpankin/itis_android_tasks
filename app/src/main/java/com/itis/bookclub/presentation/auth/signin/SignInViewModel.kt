package com.itis.bookclub.presentation.auth.signin

import androidx.lifecycle.viewModelScope
import com.itis.bookclub.domain.usecase.GetUserIdUseCase
import com.itis.bookclub.domain.usecase.IsUserAuthorizedUseCase
import com.itis.bookclub.domain.usecase.SignInUseCase
import com.itis.bookclub.domain.utils.CrashlyticsService
import com.itis.bookclub.domain.utils.ScreenEvent
import com.itis.bookclub.presentation.auth.signin.mvi.SignInAction
import com.itis.bookclub.presentation.auth.signin.mvi.SignInEvent
import com.itis.bookclub.presentation.auth.signin.mvi.SignInState
import com.itis.bookclub.presentation.base.BaseViewModel
import com.itis.bookclub.util.AppExceptionHandler
import com.itis.bookclub.util.runSuspendCatching
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase,
    private val signInUseCase: SignInUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val appExceptionHandler: AppExceptionHandler,
    private val crashlyticsService: CrashlyticsService
) : BaseViewModel<SignInState, SignInEvent, SignInAction>(
    initialState = SignInState()
) {

    init {
        checkUserAuthorized()
    }

    override fun obtainEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignInClick -> signIn()
            is SignInEvent.SignUpClick -> {
                crashlyticsService.logScreenEvent(ScreenEvent.SIGN_UP, "from sign up click")
                _actionsFlow.tryEmit(SignInAction.NavigateToSignUp)
            }
            is SignInEvent.EmailChanged -> updateEmail(event.email)
            is SignInEvent.PasswordChanged -> updatePassword(event.password)
        }
    }

    private fun checkUserAuthorized() {
        viewModelScope.launch {
            runSuspendCatching(appExceptionHandler) {
                isUserAuthorizedUseCase.invoke()
            }.onSuccess {
                crashlyticsService.logScreenEvent(ScreenEvent.MAIN, "from init")
                if (it) _actionsFlow.emit(SignInAction.NavigateToMain)
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
                crashlyticsService.logScreenEvent(ScreenEvent.MAIN, "from sign in")
                _actionsFlow.emit(SignInAction.NavigateToMain)
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isInvalidCredentials = true
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
