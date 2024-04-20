package com.onepercentbetter.login.domain.repository

import com.onepercentbetter.login.domain.model.Token

/**
 * This repository is responsible for fetching and storing a user's authentication token.
 */
interface TokenRepository {
    /**
     * Given an [token] store this somewhere so it can be retrieved later
     *
     */
    suspend fun storeToken(token: Token)

    /**
     * Fetches the auth token of the signed user, if we have one saved
     *
     * @return: The auth token or null if not found.
     */
    suspend fun fetchToken(): Token?
}
