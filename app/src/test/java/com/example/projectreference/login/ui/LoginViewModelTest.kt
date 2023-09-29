package com.example.projectreference.login.ui

import com.example.projectreference.CoroutinesTestRule
import com.example.projectreference.R
import com.example.projectreference.core.ui.components.UIText
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.Email
import com.example.projectreference.login.domain.model.LoginResult
import com.example.projectreference.login.domain.model.Password
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {
    private lateinit var testRobot: LoginViewModelRobot

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

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
            LoginViewState.Active(Credentials(Email(testEmail), Password(testPassword)))

        val expectedViewStates = listOf(
            initialState,
            emailEnteredState,
            emailAndPasswordEnteredState
        )

        testRobot
            .buildViewModel()
            .expectedViewStates(
                action = {
                    enterEmail(testEmail)
                    enterPassword(testPassword)
                },
                viewStates = expectedViewStates
            )
    }

    @Test
    fun testSubmitInvalidCredentials() = runTest {
        val testEmail = "leonardopontes.santana@gmail.com"
        val testPassword = "pass123"
        val completedCredentials = Credentials(
            email = Email(testEmail),
            password = Password(testPassword)
        )

        val initialState = LoginViewState.Initial
        val emailEnteredState = LoginViewState.Active(Credentials(Email(testEmail)))
        val emailAndPasswordEnteredState =
            LoginViewState.Active(completedCredentials)
        val submittingState = LoginViewState.Submitting(
            credentials = completedCredentials
        )
        val submissionErrorState = LoginViewState.SubmissionError(
            credentials = completedCredentials,
            errorMessage = UIText.ResourceText(R.string.error_invalid_credentials)
        )

        val expectedViewStates = listOf(
            initialState,
            emailEnteredState,
            emailAndPasswordEnteredState,
            submittingState,
            submissionErrorState
        )

        testRobot
            .buildViewModel()
            .mockLoginResultForCredentials(
                credentials = completedCredentials,
                result = LoginResult.Failure.InvalidCredentials
            )
            .expectedViewStates(
                action = {
                    enterEmail(testEmail)
                    enterPassword(testPassword)
                    clickLoginButton()
                },
                viewStates = expectedViewStates
            )
    }

    @Test
    fun testUnknownLoginFailure() = runTest {
        val testEmail = "leonardopontes.santana@gmail.com"
        val testPassword = "pass123"
        val completedCredentials = Credentials(
            email = Email(testEmail),
            password = Password(testPassword)
        )

        val initialState = LoginViewState.Initial
        val emailEnteredState = LoginViewState.Active(Credentials(Email(testEmail)))
        val emailAndPasswordEnteredState =
            LoginViewState.Active(completedCredentials)
        val submittingState = LoginViewState.Submitting(
            credentials = completedCredentials
        )
        val submissionErrorState = LoginViewState.SubmissionError(
            credentials = completedCredentials,
            errorMessage = UIText.ResourceText(R.string.error_login_failure)
        )

        val expectedViewStates = listOf(
            initialState,
            emailEnteredState,
            emailAndPasswordEnteredState,
            submittingState,
            submissionErrorState
        )

        testRobot
            .buildViewModel()
            .mockLoginResultForCredentials(
                credentials = completedCredentials,
                result = LoginResult.Failure.Unknown
            )
            .expectedViewStates(
                action = {
                    enterEmail(testEmail)
                    enterPassword(testPassword)
                    clickLoginButton()
                },
                viewStates = expectedViewStates
            )
    }

    @Test
    fun testSubmitWithoutCredentials() = runTest {

        val credentials =  Credentials()

        val initialState = LoginViewState.Initial
        val invalidInputsState = LoginViewState.Active(
            credentials = credentials,
            emailInputErrorMessage = UIText.ResourceText(R.string.error_empty_email),
            passwordEmailInputErrorMessage = UIText.ResourceText(R.string.error_empty_password)
        )

        val expectedViewStates = listOf(
            initialState,
            invalidInputsState,
        )

        testRobot
            .buildViewModel()
            .expectedViewStates(
                action = {
                    clickLoginButton()
                },
                viewStates = expectedViewStates
            )
    }
}
