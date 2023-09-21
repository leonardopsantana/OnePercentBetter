package com.example.projectreference.login.ui

import androidx.compose.runtime.currentRecomposeScope
import androidx.lifecycle.ViewModel
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.Email
import com.example.projectreference.login.domain.model.Password
import com.example.projectreference.login.domain.usecase.CredentialsLoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
        TODO()
    }

    fun signUpButtonClicked() {
        TODO()
    }

}

fun Credentials.withUpdatedEmail(email: String): Credentials {
    return this.copy(email = Email(email))
}

fun Credentials.withUpdatedPassword(password: String): Credentials {
    return this.copy(password = Password(password))
}