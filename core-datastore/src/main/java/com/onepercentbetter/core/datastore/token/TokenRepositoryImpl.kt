package com.onepercentbetter.core.datastore.token

import android.util.Log
import androidx.datastore.core.DataStore
import com.onepercentbetter.DataStoreToken
import com.onepercentbetter.core.model.AuthToken
import com.onepercentbetter.core.model.RefreshToken
import com.onepercentbetter.core.model.Token
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * This is a sample [TokenRepository] that does not interact with any real data source, but allows
 * us to quickly modify return values for manual testing sake.
 */
class TokenRepositoryImpl @Inject constructor(
    private val tokenDataStore: DataStore<DataStoreToken>,
) : TokenRepository {
    override suspend fun storeToken(
        token: Token,
    ) {
        tokenDataStore.updateData { dataStoreToken ->
            dataStoreToken
                .toBuilder()
                .setAuthToken(token.authToken.value)
                .setRefreshToken(token.refreshToken.value)
                .build()
        }
    }

    override suspend fun clearToken() {
        tokenDataStore.updateData { dataStoreToken ->
            dataStoreToken.defaultInstanceForType
        }
    }

    override fun observeToken(): Flow<Token?> {
        return tokenDataStore.data
            .map { dataStoreToken ->
                Log.d("TokenRepository", "Mapped token: ${dataStoreToken.toToken()}")
                dataStoreToken.toToken()
            }
    }
}

private fun DataStoreToken.toToken(): Token? {
    return if (this == DataStoreToken.getDefaultInstance()) {
        null
    } else {
        Token(
            authToken = AuthToken(this.authToken),
            refreshToken = RefreshToken(this.refreshToken),
        )
    }
}
