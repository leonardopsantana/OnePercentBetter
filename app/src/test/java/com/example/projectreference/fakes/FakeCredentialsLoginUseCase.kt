package com.example.projectreference.fakes

import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.LoginResult
import com.example.projectreference.login.domain.usecase.CredentialsLoginUseCase
import io.mockk.coEvery
import io.mockk.mockk

class FakeCredentialsLoginUseCase {

    val mock: CredentialsLoginUseCase = mockk()

    /**
     * Emits the supplied [result]
     */
    fun mockLoginResultForCredentials(
        credentials: Credentials,
        result: LoginResult
    ) {
        coEvery {
            mock(credentials)
        } returns result
    }
}
