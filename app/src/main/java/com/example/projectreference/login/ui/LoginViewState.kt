package com.example.projectreference.login.ui

import com.example.projectreference.core.ui.components.UIText
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
        override val credentials: Credentials,
        val emailInputErrorMessage: UIText? = null,
        val passwordInputErrorMessage: UIText? = null
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
        val errorMessage: UIText
    ) : LoginViewState(
        credentials = credentials
    )
}
