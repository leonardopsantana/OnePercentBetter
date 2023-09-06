package com.example.projectreference.login.ui

import com.example.projectreference.login.domain.model.Credentials

/**
 * A sealed class defining all possible states of our login screen.
 *
 * @property [credentials] The current credentials entered by the user.
 * @property [buttonsEnabled] The state of the buttons in the UI enabled or not
 */
sealed class LoginViewState(
    open val credentials: Credentials,
    open val buttonsEnabled: Boolean = true
) {
    /**
     * The initial state of the screen with nothing input
     */
    object Initial : LoginViewState(
        credentials = Credentials()
    )

    /**
     * The initial state of the screen as the user is entering email information
     */
    data class Active(
        override val credentials: Credentials
    ) : LoginViewState(
        credentials = credentials
    )

    /**
     * The initial state of the screen as the user is attempting to log in
     */
    data class Submitting(
        override val credentials: Credentials
    ) : LoginViewState(
        credentials = credentials,
        buttonsEnabled = false
    )

    /**
     * The initial state of the screen as some error occurred during the log in
     */
    data class SubmissionError(
        override val credentials: Credentials,
        val errorMessage: String
    ) : LoginViewState(
        credentials = credentials
    )

    /**
     * The initial state of the screen as the user inserted incorrect credentials pattern
     */
    data class InputError(
        override val credentials: Credentials,
        val emailInputErrorMessage: String?,
        val passwordEmailInputErrorMessage: String?
    ) : LoginViewState(
        credentials = credentials
    )
}
