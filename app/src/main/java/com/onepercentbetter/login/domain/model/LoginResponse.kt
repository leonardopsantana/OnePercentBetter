package com.onepercentbetter.login.domain.model

/**
 * A response from any request to login to an external service.
 */
data class LoginResponse(
    val token: Token,
)
