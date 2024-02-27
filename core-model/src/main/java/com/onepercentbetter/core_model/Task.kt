package com.onepercentbetter.core_model

import java.time.LocalDate

data class Task(
    val id: String,
    val description: String,
    val scheduledDate: LocalDate,
    val completed: Boolean
)
