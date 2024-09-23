package com.onepercentbetter.login.ui

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.fakes.FakeLoginRepository
import com.onepercentbetter.fakes.FakeTokenRepository
import com.onepercentbetter.login.domain.model.Credentials
import com.onepercentbetter.login.domain.model.LoginResponse
import com.onepercentbetter.login.domain.usecase.ProdCredentialsLoginUseCase

class LoginViewModelRobot {
    private val fakeLoginRepository = FakeLoginRepository()
    private val fakeTokenRepository = FakeTokenRepository()

    private val credentialsLoginUseCase = ProdCredentialsLoginUseCase(
        loginRepository = fakeLoginRepository.mock,
        tokenRepository = fakeTokenRepository.mock
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
            result
        )
    }

    fun enterEmail(email: String) =
        apply {
            viewModel.emailChanged(email)
        }

    fun enterPassword(password: String) =
        apply {
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

    /**
     * Launch a coroutine that will observe our [viewModel]'s view state and ensure that we consume
     * all of the supplied [viewStates] in the same order that they are supplied.
     *
     * We should call this near the front of the test, to ensure that every view state we emit
     * can be collected by this.
     */
    suspend fun expectedViewStates(
        action: LoginViewModelRobot.() -> Unit,
        viewStates: List<LoginViewState>,
    ) = apply {
        viewModel.viewState.test {
            action()

            for (state in viewStates) {
                assertThat(awaitItem()).isEqualTo(state)
            }

            this.cancel()
        }
    }
}
