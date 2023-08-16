package com.example.projectreference.login.domain.usecase

import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.LoginState

/**
 * This use case consumes any information required to log in the user and attempts to do so.
 */
interface CredentialsLoginUseCase {

    suspend operator fun invoke(
        credentials: Credentials
    ): LoginState
}
