package com.onepercentbetter.addtask.ui

import com.onepercentbetter.CoroutinesTestRule
import com.onepercentbetter.R
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.addtask.domain.model.TaskInput
import com.onepercentbetter.core.ui.components.UIText
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class AddTaskViewModelTest {
    private val testRobot = AddTaskViewModelRobot()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Test
    fun submitWithEmptyDescription() = runTest {
        val taskToSubmit = com.onepercentbetter.core_model.Task(
            id = "Testing",
            description = "X",
            scheduledDateMillis = LocalDate.now(),
            completed = false
        )

        val useCaseResult = AddTaskResult.Failure.InvalidInput(
            emptyDescription = true,
            scheduledDateInPast = false
        )

        testRobot
            .buildViewModel()
            .mockResultForTask(
                result = useCaseResult,
            )
            .expectedViewStates(
                action = {
                    enterDescription(taskToSubmit.description)
                    selectDate(taskToSubmit.scheduledDateMillis)
                    clickSubmit()
                },
                viewStates = listOf(
                    AddTaskViewState.Initial,
                    AddTaskViewState.Active(TaskInput(description = taskToSubmit.description)),
                    AddTaskViewState.Active(
                        taskInput = TaskInput(
                            description = taskToSubmit.description,
                            scheduledDate = taskToSubmit.scheduledDateMillis
                        ),
                        descriptionInputErrorMessage = UIText.ResourceText(R.string.err_empty_task_description),
                        scheduledDateInputErrorMessage = null
                    )

                )
            )
    }

    @Test
    fun submitWithInvalidDate() = runTest {
        val taskToSubmit = com.onepercentbetter.core_model.Task(
            id = "Testing",
            description = "Do something",
            scheduledDateMillis = LocalDate.now().minusDays(1),
            completed = false
        )

        val useCaseResult = AddTaskResult.Failure.InvalidInput(
            emptyDescription = false,
            scheduledDateInPast = true
        )

        testRobot
            .buildViewModel()
            .mockResultForTask(
                result = useCaseResult,
            )
            .expectedViewStates(
                action = {
                    enterDescription(taskToSubmit.description)
                    selectDate(taskToSubmit.scheduledDateMillis)
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
                            scheduledDate = taskToSubmit.scheduledDateMillis

                        ),
                        descriptionInputErrorMessage = null,
                        scheduledDateInputErrorMessage = null
                    ),
                    AddTaskViewState.Active(
                        taskInput = TaskInput(
                            description = taskToSubmit.description,
                            scheduledDate = taskToSubmit.scheduledDateMillis
                        ),
                        descriptionInputErrorMessage = null,
                        scheduledDateInputErrorMessage = UIText.ResourceText(R.string.err_scheduled_date_in_past)
                    )
                )
            )
    }
}
