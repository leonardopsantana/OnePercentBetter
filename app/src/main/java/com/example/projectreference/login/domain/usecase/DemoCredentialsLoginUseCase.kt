package com.example.projectreference.login.domain.usecase

import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.LoginResult
import kotlinx.coroutines.delay

class DemoCredentialsLoginUseCase : CredentialsLoginUseCase {
    override suspend fun invoke(credentials: Credentials): LoginResult {
        delay(2000)
       return LoginResult.Failure.InvalidCredentials
    }
}