package com.example.projectreference.fakes

import com.example.projectreference.core.data.Result
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.LoginResponse
import com.example.projectreference.login.domain.repository.LoginRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

/**
 * A fake implementation of a [LoginRepository] that wraps all of our mock work.
 */
class FakeLoginRepository {
    val mock: LoginRepository = mockk()

    fun mockLoginWithCredentials(
        credentials: Credentials,
        result: Result<LoginResponse>
    ) {
        coEvery {
            mock.login(credentials)
        } returns result
    }

    fun verifyNoLoginCall() {
        coVerify(exactly = 0) {
            mock.login(any())
        }
    }
}
