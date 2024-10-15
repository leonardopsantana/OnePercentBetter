package com.onepercentbetter.login.ui

import com.onepercentbetter.fakes.FakeLoginRepository
import com.onepercentbetter.fakes.FakeTokenRepository
import com.onepercentbetter.core.model.Credentials
import com.onepercentbetter.core.model.LoginResponse
import com.onepercentbetter.login.domain.usecase.ProdCredentialsLoginUseCase

class LoginViewModelRobot {
    private val fakeLoginRepository = FakeLoginRepository()
    private val fakeTokenRepository = FakeTokenRepository()

    private val credentialsLoginUseCase = ProdCredentialsLoginUseCase(
        loginRepository = fakeLoginRepository.mock,
        tokenRepository = fakeTokenRepository.mock,
    )

    private lateinit var viewModel: LoginViewModel

    fun buildViewModel() =
        apply {
            viewModel =
                LoginViewModel(
                    credentialsLoginUseCase = credentialsLoginUseCase,
                )
        }

    fun mockLoginResultForCredentials(
        credentials: Credentials,
        result: Result<LoginResponse>,
    ) = apply {
        fakeLoginRepository.mockLoginWithCredentials(
            credentials,
            result,
        )
    }

    fun enterEmail(
        email: String,
    ) = apply {
        viewModel.emailChanged(email)
    }

    fun enterPassword(
        password: String,
    ) = apply {
        viewModel.passwordChanged(password)
    }

    fun clickLoginButton() =
        apply {
            viewModel.loginButtonClicked()
        }

    fun clickSignUpButton() =
        apply {
            viewModel.signUpButtonClicked()
        }
}
