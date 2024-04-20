package com.onepercentbetter.login.domain.usecase

import com.onepercentbetter.login.domain.model.Credentials
import com.onepercentbetter.login.domain.model.LoginResult

/**
 * This use case consumes any information required to log in the user and attempts to do so.
 */
interface CredentialsLoginUseCase {
    suspend operator fun invoke(credentials: Credentials): LoginResult
}
