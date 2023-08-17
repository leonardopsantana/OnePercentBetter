package com.example.projectreference.login.domain.model

@JvmInline
value class Email(val value: String)

@JvmInline
value class Password(val value: String)

data class Credentials(
    val email: Email,
    val password: Password
)
