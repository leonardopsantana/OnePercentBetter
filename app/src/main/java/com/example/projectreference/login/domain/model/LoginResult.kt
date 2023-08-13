package com.example.projectreference.login.domain.model

/**
 * A collection of possible results for an attempt to login the user.
 * @param: [authToken] Token for other's requests
 */

sealed class LoginResult {
    /**
     * This attempt to login was successful
     */
    data class Success(
        val authToken: String
    ) : LoginResult()

    /**
     * This will be returned for unsuccessful when attempting to login.
     */
    sealed class Failure : LoginResult() {
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
