package com.example.projectreference.fakes

import com.example.projectreference.login.domain.model.Token
import com.example.projectreference.login.domain.repository.TokenRepository
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify

/**
 * A fake implementation of a [TokenRepository] that wraps all of our mock work.
 */
class FakeTokenRepository {
    val mock: TokenRepository = mockk(
        relaxUnitFun = true
    )

    fun verifyTokenStore(token: Token) {
        coVerify {
            mock.storeToken(token)
        }
    }

    fun verifyNoTokenStore() {
        coVerify(exactly = 0) {
            mock.storeToken(any())
        }
    }
}
