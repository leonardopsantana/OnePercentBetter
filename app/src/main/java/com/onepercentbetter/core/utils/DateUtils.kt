package com.onepercentbetter.core.utils

import java.time.LocalDate

/**
 * For this [LocalDate], parse the day of the month and calculate the suffix, for that date
 * Example: 1st, 2nd, 3rd, 4th
 */
@Suppress("MagicNumber")
fun LocalDate.getStSuffixForDayOfMonth(): String {
    return when (this.dayOfMonth) {
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
