package com.example.projectreference.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectreference.R
import com.example.projectreference.core.ui.components.UIText
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.Email
import com.example.projectreference.login.domain.model.LoginResult
import com.example.projectreference.login.domain.model.Password
import com.example.projectreference.login.domain.usecase.CredentialsLoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val credentialsLoginUseCase: CredentialsLoginUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<LoginViewState> =
        MutableStateFlow(LoginViewState.Initial)
    val viewState: StateFlow<LoginViewState> = _viewState

    fun emailChanged(email: String) {
        val currentCredentials = _viewState.value.credentials

        _viewState.value = LoginViewState.Active(
            credentials = currentCredentials.withUpdatedEmail(email = email)
        )
    }

    fun passwordChanged(password: String) {
        val currentCredentials = _viewState.value.credentials

        _viewState.value = LoginViewState.Active(
            credentials = currentCredentials.withUpdatedPassword(password = password)
        )
    }

    fun loginButtonClicked() {
        val currentCredentials = _viewState.value.credentials

        _viewState.value = LoginViewState.Submitting(credentials = currentCredentials)

        viewModelScope.launch {
            val loginResult = credentialsLoginUseCase(currentCredentials)

            _viewState.value = when (loginResult) {
                LoginResult.Failure.InvalidCredentials -> {
                    LoginViewState.SubmissionError(
                        credentials = currentCredentials,
                        errorMessage = UIText.ResourceText(R.string.error_invalid_credentials)
                    )
                }

                LoginResult.Failure.Unknown -> LoginViewState.SubmissionError(
                    credentials = currentCredentials,
                    errorMessage = UIText.ResourceText(R.string.error_login_failure)
                )

                LoginResult.Success -> _viewState.value
                is LoginResult.Failure.EmptyCredentials -> {
                    loginResult.toActiveLoginViewState(currentCredentials)
                }
            }
        }
    }

    fun signUpButtonClicked() {
        TODO()
    }

    /**
     * Given some [credentials], ensure that we've been provided valid information that can used
     * to log in. If not, update the current [viewState] accordingly, and return whether or not to
     * to proceed.
     */
    private fun validateCredentials(credentials: Credentials): Boolean {
        val hasEmail = credentials.email.value.isNotEmpty()
        val hasPassword = credentials.password.value.isNotEmpty()

        _viewState.value = LoginViewState.Active(
            credentials = credentials,
            emailInputErrorMessage = if (hasEmail) null else UIText.ResourceText(R.string.error_empty_email),
            passwordEmailInputErrorMessage = if (hasPassword) null else UIText.ResourceText(R.string.error_empty_password)
        )

        return hasEmail && hasPassword
    }
}

private fun Credentials.withUpdatedEmail(email: String): Credentials {
    return this.copy(email = Email(email))
}

private fun Credentials.withUpdatedPassword(password: String): Credentials {
    return this.copy(password = Password(password))
}

private fun LoginResult.Failure.EmptyCredentials.toActiveLoginViewState(
    currentCredentials: Credentials,
): LoginViewState {
    return LoginViewState.Active(
        credentials = currentCredentials,
        emailInputErrorMessage = UIText.ResourceText(R.string.error_empty_email)
            .takeIf { this.emptyEmail },
        passwordEmailInputErrorMessage = UIText.ResourceText(R.string.error_empty_password)
            .takeIf { this.emptyEmail }
    )
}