package com.onepercentbetter.addtask.ui

import com.onepercentbetter.addtask.domain.model.TaskInput
import com.onepercentbetter.core.ui.components.UIText
import java.time.LocalDate

/**
 * A collection of possible view states for the add task UI.
 */
sealed class AddTaskViewState(
    open val taskInput: TaskInput = TaskInput(),
    val inputsEnabled: Boolean = true,
) {
    data class Initial(
        val initDate: LocalDate = LocalDate.now(),
    ) : AddTaskViewState(
            taskInput = TaskInput(
                scheduledDate = initDate,
            ),
        )

    data class Active(
        override val taskInput: TaskInput,
        val descriptionInputErrorMessage: UIText? = null,
    ) : AddTaskViewState(
            taskInput = taskInput,
        )

    data class Submitting(
        override val taskInput: TaskInput,
    ) : AddTaskViewState(
            taskInput = taskInput,
            inputsEnabled = false,
        )

    data class SubmissionError(
        override val taskInput: TaskInput,
        val errorMessage: UIText,
        val overrideButtonText: UIText? = null,
        val allowRetry: Boolean = false,
    ) : AddTaskViewState(
            taskInput = taskInput,
        )

    object Completed : AddTaskViewState(
        taskInput = TaskInput(),
        inputsEnabled = false,
    )
}
