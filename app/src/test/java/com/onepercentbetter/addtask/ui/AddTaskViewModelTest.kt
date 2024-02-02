package com.onepercentbetter.addtask.ui

import com.onepercentbetter.CoroutinesTestRule
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.addtask.domain.model.TaskInput
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.tasklist.domain.model.Task
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import com.onepercentbetter.R
import kotlinx.coroutines.test.runTest

class AddTaskViewModelTest {
    private val testRobot = AddTaskViewModelRobot()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Test
    fun submitWithEmptyDescription() = runTest {
        val taskToSubmit = Task(
            id = "Testing",
            description = "X",
            scheduledDate = LocalDate.now()
        )

        val useCaseResult = AddTaskResult.Failure.InvalidInput(
            emptyDescription = true,
            scheduledDateInPast = false
        )

        testRobot
            .buildViewModel()
            .mockResultForTask(
                task = taskToSubmit,
                result = useCaseResult,
            )
            .expectedViewStates(
                action = {
                    enterDescription(taskToSubmit.description)
                    selectDate(taskToSubmit.scheduledDate)
                    clickSubmit()
                },
                viewStates = listOf(
                    AddTaskViewState.Initial,
                    AddTaskViewState.Active(TaskInput(description = taskToSubmit.description)),
                    AddTaskViewState.Active(
                        taskInput = TaskInput(
                            description = taskToSubmit.description,
                            scheduledDate = taskToSubmit.scheduledDate
                        ),
                        descriptionInputErrorMessage = UIText.ResourceText(R.string.err_empty_task_description),
                        scheduledDateInputErrorMessage = null
                    )

                )
            )
    }

    @Test
    fun submitWithInvalidDate() = runTest {
        val taskToSubmit = Task(
            id = "Testing",
            description = "Do something",
            scheduledDate = LocalDate.now().minusDays(1)
        )

        val useCaseResult = AddTaskResult.Failure.InvalidInput(
            emptyDescription = false,
            scheduledDateInPast = true
        )

        testRobot
            .buildViewModel()
            .mockResultForTask(
                task = taskToSubmit,
                result = useCaseResult,
            )
            .expectedViewStates(
                action = {
                    enterDescription(taskToSubmit.description)
                    selectDate(taskToSubmit.scheduledDate)
                    clickSubmit()
                },
                viewStates = listOf(
                    AddTaskViewState.Initial,
                    AddTaskViewState.Active(
                        TaskInput(
                            description = taskToSubmit.description,
                        )
                    ),
                    AddTaskViewState.Active(
                        taskInput = TaskInput(
                            description = taskToSubmit.description,
                            scheduledDate = taskToSubmit.scheduledDate

                        ),
                        descriptionInputErrorMessage = null,
                        scheduledDateInputErrorMessage = null
                    ),
                    AddTaskViewState.Active(
                        taskInput = TaskInput(
                            description = taskToSubmit.description,
                            scheduledDate = taskToSubmit.scheduledDate
                        ),
                        descriptionInputErrorMessage = null,
                        scheduledDateInputErrorMessage = UIText.ResourceText(R.string.err_scheduled_date_in_past)
                    )
                )
            )
    }
}