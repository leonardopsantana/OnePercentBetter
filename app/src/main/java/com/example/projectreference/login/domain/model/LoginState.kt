package com.example.projectreference.login.domain.model

/**
 * A collection of possible results for an attempt to login the user.
 */

sealed class LoginState {
    /**
     * This attempt to login was successful
     */
    object Success : LoginState()

    /**
     * This will be returned for unsuccessful when attempting to login.
     */
    sealed class Failure : LoginState() {
        /**
         * This will be returned if there was no account matching the requested credentials.
         */
        object InvalidCredentials : Failure()

        /**
         * This will be returned if there was any unknown errors
         */
        object Unknown : Failure()
    }
}
