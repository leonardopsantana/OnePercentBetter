package com.onepercentbetter.core.model

data class Task(
    val id: String,
    val description: String,
    val scheduledDateMillis: Long,
    val completed: Boolean,
)
