package com.onepercentbetter.login.ui

import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.login.domain.model.Credentials

/**
 * A sealed class defining all possible states of our login screen.
 *
 * @property [credentials] The current credentials entered by the user.
 * @property [inputsEnabled] The state of the buttons in the UI enabled or not
 */
sealed class LoginViewState(
    open val credentials: Credentials,
    open val inputsEnabled: Boolean = true,
) {
    /**
     * The initial state of the screen with nothing input
     */
    object Initial : LoginViewState(
        credentials = Credentials(),
    )

    /**
     * The initial state of the screen as the user is entering email information
     */
    data class Active(
        override val credentials: Credentials,
        val emailInputErrorMessage: UIText? = null,
        val passwordInputErrorMessage: UIText? = null,
    ) : LoginViewState(
            credentials = credentials,
        )

    /**
     * The initial state of the screen as the user is attempting to log in
     */
    data class Submitting(
        override val credentials: Credentials,
    ) : LoginViewState(
            credentials = credentials,
            inputsEnabled = false,
        )

    /**
     * The initial state of the screen as some error occurred during the log in
     */
    data class SubmissionError(
        override val credentials: Credentials,
        val errorMessage: UIText,
    ) : LoginViewState(
            credentials = credentials,
        )

    /**
     * The state when the user logs in with success
     */
    object Completed : LoginViewState(
        credentials = Credentials(),
        inputsEnabled = false,
    )
}
