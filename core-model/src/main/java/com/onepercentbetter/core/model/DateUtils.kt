package com.onepercentbetter.core.model

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * For this [LocalDate], parse the day of the month and calculate the suffix for that day.
 * Example: 1st, 2nd, 3rd, 4th, etc...
 */
@Suppress("MagicNumber")
fun LocalDate.getSuffixForDayOfMonth(): String {
    val dayOfMonth = this.dayOfMonth

    return when (dayOfMonth) {
        in 11..13 -> "th"
        else -> {
            when (dayOfMonth % 10) {
                1 -> "st"
                2 -> "nd"
                3 -> "rd"
                else -> "th"
            }
        }
    }
}

fun LocalDate.toEpochMillis(
    zoneId: ZoneId = ZoneId.systemDefault(),
): Long {
    return this.atStartOfDay()
        .atZone(zoneId)
        .toInstant()
        .toEpochMilli()
}

fun LocalDate.toEpochMillisUTC(): Long = this.toEpochMillis(ZoneId.of("UTC"))

fun Long.toLocalDate(
    zoneId: ZoneId = ZoneId.systemDefault(),
): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(zoneId)
        .toLocalDate()
}

fun Long.toLocalDateUTC(): LocalDate = this.toLocalDate(ZoneId.of("UTC"))
