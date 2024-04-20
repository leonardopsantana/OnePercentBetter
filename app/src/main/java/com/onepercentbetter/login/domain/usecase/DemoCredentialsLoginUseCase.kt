package com.onepercentbetter.login.domain.usecase

import com.onepercentbetter.login.domain.model.Credentials
import com.onepercentbetter.login.domain.model.LoginResult
import kotlinx.coroutines.delay

class DemoCredentialsLoginUseCase : CredentialsLoginUseCase {
    @Suppress("MagicNumber")
    override suspend fun invoke(credentials: Credentials): LoginResult {
        delay(2000)
        return LoginResult.Failure.InvalidCredentials
    }
}
