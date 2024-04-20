package com.onepercentbetter.tasklist.ui

import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.core.utils.getStSuffixForDayOfMonth
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskListViewStateTest {
    @Test
    fun parseDateStringForYesterday() {
        val yesterday = LocalDate.now().minusDays(1)

        val viewState =
            TaskListViewState(
                selectedDate = yesterday,
            )

        val expectedString = UIText.ResourceText(R.string.yesterday)
        assertThat(viewState.selectedDateString).isEqualTo(expectedString)
    }

    @Test
    fun parseDateStringForToday() {
        val today = LocalDate.now()

        val viewState =
            TaskListViewState(
                selectedDate = today,
            )

        val expectedString = UIText.ResourceText(R.string.today)
        assertThat(viewState.selectedDateString).isEqualTo(expectedString)
    }

    @Test
    fun parseDateStringForTomorrow() {
        val tomorrow = LocalDate.now().plusDays(1)

        val viewState =
            TaskListViewState(
                selectedDate = tomorrow,
            )

        val expectedString = UIText.ResourceText(R.string.tomorrow)
        assertThat(viewState.selectedDateString).isEqualTo(expectedString)
    }

    @Test
    fun parseDateStringForFuture() {
        val twoDaysFromNow = LocalDate.now().plusDays(2)

        val viewState =
            TaskListViewState(
                selectedDate = twoDaysFromNow,
            )

        val expectedDateFormat = "MMM dd"
        val expectedSuffix = twoDaysFromNow.getStSuffixForDayOfMonth()
        val parsedDateString = DateTimeFormatter.ofPattern(expectedDateFormat).format(twoDaysFromNow)
        val expectedDateString = "$parsedDateString$expectedSuffix"
        val expectedString = UIText.StringText(expectedDateString)

        assertThat(viewState.selectedDateString).isEqualTo(expectedString)
    }
}
