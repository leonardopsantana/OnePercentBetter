package com.onepercentbetter.fakes

import com.onepercentbetter.core.datastore.token.TokenRepository
import com.onepercentbetter.core.model.Token
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FakeTokenRepository @Inject constructor() : com.onepercentbetter.core.datastore.token.TokenRepository {
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

    override fun observeToken(): Flow<Token?> = tokenFlow
}
