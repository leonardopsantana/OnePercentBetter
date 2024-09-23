package com.onepercentbetter.tasklist.ui

import com.onepercentbetter.R
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.core.ui.AlertMessage
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.core.utils.getStSuffixForDayOfMonth
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * All of the necessary configuration for the task list screen UI.
 *
 * @property[errorMessage] Unlike [alertMessage], this is used signify a problem requesting data
 * such as an inability to fetch tasks. It will be shown within the content of the screen.
 * @property[taskToReschedule] If this is not null, this is  the [Task] entity
 * that the user is currently rescheduling.
 * @property[alertMessage] Unlike [errorMessage], this message will presented tp the user in sort of
 * alerting fashion like a snackbar, dialog, or other way of grabbing their attention.
 * This signifies a problem processing user input.
 */
data class TaskListViewState(
    val showLoading: Boolean = true,
    val completedTasks: List<Task>? = null,
    val incompleteTasks: List<Task>? = null,
    val errorMessage: UIText? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val taskToReschedule: Task? = null,
    val alertMessages: List<AlertMessage> = emptyList()
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
