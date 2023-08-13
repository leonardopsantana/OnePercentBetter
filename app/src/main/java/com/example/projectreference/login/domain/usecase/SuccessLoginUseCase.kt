package com.example.projectreference.login.domain.usecase

import com.example.projectreference.login.domain.model.LoginResult

/**
 * This is temp class to create a mock implementation of [LoginUseCase] that is always successful
 */
class SuccessLoginUseCase : LoginUseCase {
    override suspend fun invoke(email: Email, password: Password): LoginResult {
        return LoginResult.Success("")
    }
}
