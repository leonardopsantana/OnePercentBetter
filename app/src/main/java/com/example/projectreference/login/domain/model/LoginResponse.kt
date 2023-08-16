package com.example.projectreference.login.domain.model

/**
 * A response from any request to login to an external service.
 */
data class LoginResponse(
    private val authToken: String
)