package com.onepercentbetter.fakes

import com.onepercentbetter.login.domain.model.Token
import com.onepercentbetter.login.domain.repository.TokenRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FakeTokenRepository @Inject constructor() : TokenRepository {
    private var tokenFlow = MutableSharedFlow<Token?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override suspend fun storeToken(
        token: Token,
    ) {
        tokenFlow.emit(token)
    }

    override suspend fun clearToken() {
        tokenFlow.emit(null)
    }

    override fun observeToken(): Flow<Token?> {
        return tokenFlow
    }
}
