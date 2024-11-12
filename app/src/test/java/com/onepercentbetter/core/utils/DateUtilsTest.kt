package com.onepercentbetter.core.utils

import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.core.model.getSuffixForDayOfMonth
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class DateUtilsTest {
    @Test
    fun getStSuffix() {
        listOf(1, 21, 31).convertToSuffixes().forEach { suffix ->
            assertThat(suffix).isEqualTo("st")
        }
    }

    @Test
    fun getNdSuffix() {
        listOf(2, 22).convertToSuffixes().forEach { suffix ->
            assertThat(suffix).isEqualTo("nd")
        }
    }

    @Test
    fun getRdSuffix() {
        listOf(3, 23).convertToSuffixes().forEach { suffix ->
            assertThat(suffix).isEqualTo("rd")
        }
    }

    @Test
    fun getThSuffix() {
        listOf(
            (4..9),
            (10..20),
            (24..30),
        ).flatten()
            .convertToSuffixes()
            .forEach { suffix ->
                assertThat(suffix).isEqualTo("th")
            }
    }
}

private fun List<Int>.convertToSuffixes(): List<String> =
    this.map { day ->
        LocalDate.of(2020, Month.JANUARY, day).getSuffixForDayOfMonth()
    }
