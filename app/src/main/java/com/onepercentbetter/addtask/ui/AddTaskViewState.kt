package com.onepercentbetter.addtask.ui

import com.onepercentbetter.addtask.domain.model.TaskInput
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.tasklist.domain.model.Task
import java.time.LocalDate

/**
 * A collection of possible view states for the add task UI.
 */
sealed class AddTaskViewState(
    open val taskInput: TaskInput = TaskInput(),
    val inputsEnabled: Boolean = true
) {

    object Initial : AddTaskViewState(
        taskInput = TaskInput()
    )

    data class Active(
        override val taskInput: TaskInput,
    ) : AddTaskViewState(
        taskInput = taskInput,
    )

    data class Submitting(
        override val taskInput: TaskInput,
    ) : AddTaskViewState(
        taskInput = taskInput,
        inputsEnabled = false
    )

    data class SubmissionError(
        override val taskInput: TaskInput,
        val errorMessage: UIText
    ) : AddTaskViewState(
        taskInput = taskInput,
    )

    object Completed : AddTaskViewState(
        taskInput = TaskInput()
    )
}