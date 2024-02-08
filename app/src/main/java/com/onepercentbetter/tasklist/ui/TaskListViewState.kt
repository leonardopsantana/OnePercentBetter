package com.onepercentbetter.tasklist.ui

import com.onepercentbetter.R
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.tasklist.domain.model.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class TaskListViewState(
    val showLoading: Boolean = true,
    val tasks: List<Task>? = null,
    val errorMessage: UIText? = null,
    val selectedDate: LocalDate = LocalDate.now()
) {

    val selectedDateString: UIText
        get() {
            val isToday = (selectedDate == LocalDate.now())
            val isTomorrow = (selectedDate == LocalDate.now().plusDays(1))

            return when {
                isToday -> UIText.ResourceText(R.string.today)
                isTomorrow -> UIText.ResourceText(R.string.tomorrow)
                else -> {
                    val uiDateFormat = "dd MMMM, yyyy"

                    val uiString = DateTimeFormatter.ofPattern(uiDateFormat).format(selectedDate)

                    UIText.StringText(uiString)
                }
            }
        }

}
