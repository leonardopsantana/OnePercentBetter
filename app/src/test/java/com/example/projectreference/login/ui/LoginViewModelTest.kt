package com.example.projectreference.login.ui

import com.example.projectreference.R
import com.example.projectreference.core.ui.components.UIText
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.Email
import com.example.projectreference.login.domain.model.LoginResult
import com.example.projectreference.login.domain.model.Password
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
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
    fun testUpdateCredentials() = runTest {
        val testEmail = "leonardopontes.santana@gmail.com"
        val testPassword = "pass123"

        val initialState = LoginViewState.Initial
        val emailEnteredState = LoginViewState.Active(Credentials(Email(testEmail)))
        val emailAndPasswordEnteredState =
            LoginViewState.Active(Credentials(Email(testPassword), Password(testPassword)))

        val expectedViewStates = listOf(
            initialState,
            emailEnteredState,
            emailAndPasswordEnteredState
        )

        testRobot
            .buildViewModel()
            .assertViewStatesAfterAction(
                action = {
                    this.enterEmail(testEmail)
                    this.enterPassword(testPassword)
                },
                viewStates = expectedViewStates
            )
    }

//    @Test
//    fun testInvalidCredentialLogin() = runTest {
//
//        val credentials = defaultCredentials
//
//        testRobot.buildViewModel()
//            .enterEmail(credentials.email.value)
//            .enterPassword(credentials.password.value)
//            .clickLoginButton()
//            .assertViewState(
//                LoginViewState.Submitting(
//                    credentials = credentials
//                )
//            )
//            .mockLoginResultForCredentials(
//                credentials = credentials,
//                result = LoginResult.Failure.InvalidCredentials
//            )
//            .assertViewStates(
//                listOf(
//                    LoginViewState.Initial,
//                    LoginViewState.SubmissionError(
//                        credentials = defaultCredentials,
//                        errorMessage = UIText.ResourceText(R.string.error_invalid_credentials)
//                    )
//                )
//            )
//    }
}