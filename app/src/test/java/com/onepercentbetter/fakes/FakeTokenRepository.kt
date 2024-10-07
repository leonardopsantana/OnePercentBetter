package com.onepercentbetter.fakes

import com.onepercentbetter.login.domain.model.Token
import com.onepercentbetter.login.domain.repository.TokenRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FakeTokenRepository {
    val mock: TokenRepository = mockk(
        relaxUnitFun = true,
    )

    fun verifyTokenStored(
        token: Token,
    ) {
        coVerify {
            mock.storeToken(token)
        }
    }

    fun verifyNoTokenStored() {
        coVerify(exactly = 0) {
            mock.storeToken(any())
        }
    }
}
