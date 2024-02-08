package com.onepercentbetter.tasklist.ui

import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.core.ui.components.UIText
import org.junit.Test
import java.time.LocalDate
import com.onepercentbetter.R
import java.time.format.DateTimeFormatter


class TaskListViewStateTest {

    @Test
    fun parseDateStringForToday() {
        val today = LocalDate.now()

        val viewState = TaskListViewState(
            selectedDate = today
        )

        val expectedString = UIText.ResourceText(R.string.today)
        assertThat(viewState.selectedDateString).isEqualTo(expectedString)
    }

    @Test
    fun parseDateStringForTomorrow() {
        val tomorrow = LocalDate.now().plusDays(1)

        val viewState = TaskListViewState(
            selectedDate = tomorrow
        )

        val expectedString = UIText.ResourceText(R.string.tomorrow)
        assertThat(viewState.selectedDateString).isEqualTo(expectedString)
    }

    @Test
    fun parseDateStringForFuture() {
        val twoDaysFromNow = LocalDate.now().plusDays(2)

        val viewState = TaskListViewState(
            selectedDate = twoDaysFromNow
        )

        val expectedDateFormat = "dd MMMM, yyyy"
        val expectedDateString = DateTimeFormatter.ofPattern(expectedDateFormat).format(twoDaysFromNow)
        val expectedString = UIText.StringText(expectedDateString)

        assertThat(viewState.selectedDateString).isEqualTo(expectedString)
    }
}