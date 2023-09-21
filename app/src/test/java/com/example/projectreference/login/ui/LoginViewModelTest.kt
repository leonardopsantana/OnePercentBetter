package com.example.projectreference.login.ui

import com.example.projectreference.R
import com.example.projectreference.core.ui.components.UIText
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.Email
import com.example.projectreference.login.domain.model.LoginResult
import com.example.projectreference.login.domain.model.Password
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {
    private lateinit var testRobot: LoginViewModelRobot

    private val defaultCredentials = Credentials(
        email = Email("leonardopontes.santana@gmail.com"),
        password = Password("dummyPass")
    )

    @Before
    fun setUp() {
        testRobot = LoginViewModelRobot()
    }

    @Test
    fun testInitialState() {
        testRobot
            .buildViewModel()
            .assertViewState(LoginViewState.Initial)
    }

    @Test
    fun testUpdateCredentials() {
        val credentials = defaultCredentials

        testRobot.buildViewModel()
            .enterEmail(credentials.email.value)
            .enterPassword(credentials.password.value)
            .assertViewState(LoginViewState.Active(credentials))
    }

    @Test
    fun testInvalidCredentialLogin() {
        val credentials = defaultCredentials

        testRobot.buildViewModel()
            .enterEmail(credentials.email.value)
            .enterPassword(credentials.password.value)
            .clickLoginButton()
            .assertViewState(
                LoginViewState.Submitting(
                    credentials = credentials
                )
            )
            .mockLoginResultForCredentials(
                credentials = credentials,
                result = LoginResult.Failure.InvalidCredentials
            )
            .assertViewState(
                LoginViewState.SubmissionError(
                    credentials = defaultCredentials,
                    errorMessage = UIText.ResourceText(R.string.error_invalid_credentials)
                )
            )
    }

}