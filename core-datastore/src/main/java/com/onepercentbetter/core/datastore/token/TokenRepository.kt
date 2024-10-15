package com.onepercentbetter.core.datastore.token

import com.onepercentbetter.core.model.Token
import kotlinx.coroutines.flow.Flow

/**
 * This repository is responsible for fetching and storing a user's authentication token.
 */
interface TokenRepository {
    /**
     * Given an [token] store this somewhere so it can be retrieved later
     *
     */
    suspend fun storeToken(
        token: Token,
    )

    /**
     * Clear any cached authentication token.
     */
    suspend fun clearToken()

    /**
     * Fetches the auth token of the signed user, if we have one saved
     *
     * @return: The auth token or null if not found.
     */
    fun observeToken(): Flow<Token?>
}
