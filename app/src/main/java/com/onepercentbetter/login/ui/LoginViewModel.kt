package com.onepercentbetter.login.ui

import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.login.domain.model.Credentials
import com.onepercentbetter.login.domain.model.Email
import com.onepercentbetter.login.domain.model.LoginResult
import com.onepercentbetter.login.domain.model.Password
import com.onepercentbetter.login.domain.usecase.CredentialsLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val credentialsLoginUseCase: CredentialsLoginUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<LoginViewState> =
        MutableStateFlow(LoginViewState.Initial)
    val viewState: StateFlow<LoginViewState> = _viewState

    val loginCompletedChannel = Channel<Unit>()

    fun emailChanged(email: String) {
        val currentCredentials = _viewState.value.credentials
        val currentPasswordErrorMessage =
            (_viewState.value as? LoginViewState.Active)?.passwordInputErrorMessage

        _viewState.value = LoginViewState.Active(
            credentials = currentCredentials.withUpdatedEmail(email),
            emailInputErrorMessage = null,
            passwordInputErrorMessage = currentPasswordErrorMessage,
        )
    }

    fun passwordChanged(password: String) {
        val currentCredentials = _viewState.value.credentials
        val currentEmailErrorMessage =
            (_viewState.value as? LoginViewState.Active)?.emailInputErrorMessage

        _viewState.value = LoginViewState.Active(
            credentials = currentCredentials.withUpdatedPassword(password),
            passwordInputErrorMessage = null,
            emailInputErrorMessage = currentEmailErrorMessage,
        )
    }

    fun loginButtonClicked() {
        val currentCredentials = _viewState.value.credentials

        _viewState.value = LoginViewState.Submitting(
            credentials = currentCredentials,
        )

        viewModelScope.launch {
            val loginResult = credentialsLoginUseCase(currentCredentials)

            handleLoginResult(currentCredentials, loginResult)
        }
    }

    fun onInputFocused(focusState: FocusState) {

    }

    private fun handleLoginResult(currentCredentials: Credentials, loginResult: LoginResult) {
        _viewState.value = when (loginResult) {
            is LoginResult.Failure.InvalidCredentials -> {
                LoginViewState.SubmissionError(
                    credentials = currentCredentials,
                    errorMessage = UIText.ResourceText(R.string.error_invalid_credentials),
                )
            }

            is LoginResult.Failure.Unknown -> {
                LoginViewState.SubmissionError(
                    credentials = currentCredentials,
                    errorMessage = UIText.ResourceText(R.string.error_login_failure),
                )
            }

            is LoginResult.Success -> {
                viewModelScope.launch {
                    loginCompletedChannel.send(Unit)
                }
                LoginViewState.Completed
            }

            is LoginResult.Failure.EmptyCredentials -> {
                loginResult.toActiveLoginViewState(currentCredentials)
            }

            else -> _viewState.value
        }
    }

    fun signUpButtonClicked() {
        TODO()
    }
}

private fun Credentials.withUpdatedEmail(email: String): Credentials {
    return this.copy(email = Email(email))
}

private fun Credentials.withUpdatedPassword(password: String): Credentials {
    return this.copy(password = Password(password))
}

private fun LoginResult.Failure.EmptyCredentials.toActiveLoginViewState(credentials: Credentials): LoginViewState {
    return LoginViewState.Active(
        credentials = credentials,
        emailInputErrorMessage = UIText.ResourceText(R.string.error_empty_email).takeIf {
            this.emptyEmail
        },
        passwordInputErrorMessage = UIText.ResourceText(R.string.error_empty_password).takeIf {
            this.emptyPassword
        },
    )
}
