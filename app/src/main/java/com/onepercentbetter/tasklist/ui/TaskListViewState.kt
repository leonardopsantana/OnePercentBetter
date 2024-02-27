package com.onepercentbetter.tasklist.ui

import com.onepercentbetter.R
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.core.utils.getStSuffixForDayOfMonth
import com.onepercentbetter.core_model.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class TaskListViewState(
    val showLoading: Boolean = true,
    val completedTasks: List<com.onepercentbetter.core_model.Task>? = null,
    val incompleteTasks: List<com.onepercentbetter.core_model.Task>? = null,
    val errorMessage: UIText? = null,
    val selectedDate: LocalDate = LocalDate.now()
) {

    /**
     * As long as we are not in a loading error scenario, we can show the task list (or empty state)
     */
    val showTasks: Boolean
        get() = !showLoading && errorMessage == null

    val selectedDateString: UIText
        get() {
            val today = LocalDate.now()
            val isYesterday = (selectedDate == today.minusDays(1))
            val isToday = (selectedDate == today)
            val isTomorrow = (selectedDate == today.plusDays(1))

            return when {
                isYesterday -> UIText.ResourceText(R.string.yesterday)
                isToday -> UIText.ResourceText(R.string.today)
                isTomorrow -> UIText.ResourceText(R.string.tomorrow)
                else -> {
                    val uiDateFormat = "MMM dd"
                    val suffix = selectedDate.getStSuffixForDayOfMonth()

                    val dateString = DateTimeFormatter.ofPattern(uiDateFormat).format(selectedDate)

                    UIText.StringText("$dateString$suffix")
                }
            }
        }
}
