package com.onepercentbetter.login.ui

import com.onepercentbetter.CoroutinesTestRule
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.login.domain.model.Credentials
import com.onepercentbetter.login.domain.model.Email
import com.onepercentbetter.login.domain.model.LoginResult
import com.onepercentbetter.login.domain.model.Password
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

        val credentials = Credentials()

        val initialState = LoginViewState.Initial
        val submittingState = LoginViewState.Submitting(credentials = credentials)
        val invalidInputsState = LoginViewState.Active(
            credentials = credentials,
            emailInputErrorMessage = UIText.ResourceText(R.string.error_empty_email),
            passwordInputErrorMessage = UIText.ResourceText(R.string.error_empty_password)
        )

        val expectedViewStates = listOf(
            initialState,
            submittingState,
            invalidInputsState,
        )

        testRobot
            .buildViewModel()
            .mockLoginResultForCredentials(
                credentials = credentials,
                result = LoginResult.Failure.EmptyCredentials(
                    emptyEmail = true,
                    emptyPassword = true
                )
            )
            .expectedViewStates(
                action = {
                    clickLoginButton()
                },
                viewStates = expectedViewStates
            )
    }

//    @Test
//    fun testClearErrorsAfterInput() = runTest {
//        val credentials = Credentials()
//        val testEmail = "leonardopontes.santana@mail.com"
//        val testPassword = "Hunter2"
//
//        val initialState = LoginViewState.Initial
//        val submittingState = LoginViewState.Submitting(
//            credentials = credentials,
//        )
//        val invalidInputsState = LoginViewState.Active(
//            credentials = credentials,
//            emailInputErrorMessage = UIText.ResourceText(R.string.error_empty_email),
//            passwordInputErrorMessage = UIText.ResourceText(R.string.error_empty_password),
//        )
//        val emailInputState = LoginViewState.Active(
//            credentials = Credentials(email = Email(testEmail)),
//            emailInputErrorMessage = null,
//            passwordInputErrorMessage = UIText.ResourceText(R.string.error_empty_password),
//        )
//        val passwordInputState = LoginViewState.Active(
//            credentials = Credentials(email = Email(testEmail), password = Password(testPassword)),
//            emailInputErrorMessage = null,
//            passwordInputErrorMessage = null,
//        )
//
//        testRobot
//            .buildViewModel()
//            .mockLoginResultForCredentials(
//                credentials = credentials,
//                result = LoginResult.Failure.EmptyCredentials(
//                    emptyEmail = true,
//                    emptyPassword = true,
//                )
//            )
//            .expectedViewStates(
//                action = {
//                    clickLoginButton()
//                    enterEmail(testEmail)
// //                    enterPassword(testPassword)
//                },
//                viewStates = listOf(
//                    initialState,
//                    submittingState,
//                    invalidInputsState,
//                    emailInputState,
// //                    passwordInputState,
//                ),
//            )
//    }
}
