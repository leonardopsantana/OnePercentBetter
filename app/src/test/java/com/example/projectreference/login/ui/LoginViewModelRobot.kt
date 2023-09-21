package com.example.projectreference.login.ui

import com.example.projectreference.fakes.FakeCredentialsLoginUseCase
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.LoginResult
import com.google.common.truth.Truth.assertThat

class LoginViewModelRobot {
    private val fakeCredentialsLoginUseCase = FakeCredentialsLoginUseCase()
    private lateinit var viewModel: LoginViewModel

    fun buildViewModel() = apply {
        viewModel = LoginViewModel(
            credentialsLoginUseCase = fakeCredentialsLoginUseCase.mock,
        )
    }

    fun mockLoginResultForCredentials(
        credentials: Credentials,
        result: LoginResult
    ) = apply {
        fakeCredentialsLoginUseCase.mockLoginResultForCredentials(credentials, result)
    }

    fun enterEmail(email: String) = apply {
        viewModel.emailChanged(email)
    }

    fun enterPassword(password: String) = apply {
        viewModel.passwordChanged(password)
    }

    fun clickLoginButton() = apply {
        viewModel.loginButtonClicked()
    }

    fun clickSignUpButton() = apply {
        viewModel.signUpButtonClicked()
    }

    fun assertViewState(expectedViewState: LoginViewState) = apply {
        assertThat(viewModel.viewState.value).isEqualTo(expectedViewState)
    }
}