package com.onepercentbetter.addtask.ui

import com.onepercentbetter.InstantTaskCoroutinesExecutorRule
import com.onepercentbetter.R
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.addtask.domain.model.TaskInput
import com.onepercentbetter.core.assertViewStates
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.core.model.toEpochMillis
import com.onepercentbetter.core.ui.components.UIText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID

@ExperimentalCoroutinesApi
class AddTaskViewModelTest {
    private val testRobot = AddTaskViewModelRobot()

    @get:Rule
    val coroutinesTestRule = InstantTaskCoroutinesExecutorRule()

    @Test
    fun `GIVEN an initial date, WHEN adding a task THEN assert state IS Initial`() =
        runTest {
            // GIVEN
            val initialDate = LocalDate.now().plusDays(1)

            val expectedViewState = AddTaskViewState.Initial(initialDate)

            testRobot
                .mockInitialDate(initialDate)
                .buildViewModel(coroutinesTestRule.dispatcher)
                .assertViewStates(
                    stateUnderTest = testRobot.viewModel.viewState,
                    // WHEN
                    action = {},
                    // THEN
                    expectedViewStates = listOf(expectedViewState),
                )
        }

    @Test
    fun `GIVEN an empty description, WHEN adding a task THEN assert state IS active with input error message`() =
        runTest {
            // GIVEN
            val initialDate = LocalDate.now()
            val taskToSubmit = Task(
                id = UUID.randomUUID().toString(),
                description = "",
                scheduledDateMillis = initialDate.toEpochMillis(),
                completed = false,
            )

            val taskInput = TaskInput(
                description = taskToSubmit.description,
                scheduledDate = Instant
                    .ofEpochMilli(taskToSubmit.scheduledDateMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate(),
            )

            testRobot
                .mockInitialDate(initialDate)
                .buildViewModel(coroutinesTestRule.dispatcher)
                .mockResultForTask(
                    taskToSubmit,
                    AddTaskResult.Failure.InvalidInput(
                        emptyDescription = true,
                        scheduledDateInPast = false,
                    ),
                ).assertViewStates(
                    stateUnderTest = testRobot.viewModel.viewState,
                    // WHEN
                    action = {
                        enterDescription(taskToSubmit.description)
                        selectDate(taskToSubmit.scheduledDateMillis)
                        clickSubmit(taskToSubmit)
                    },
                    // THEN
                    expectedViewStates = listOf(
                        AddTaskViewState.Initial(
                            initDate = initialDate,
                        ),
                        AddTaskViewState.Active(
                            taskInput = taskInput.copy(scheduledDate = initialDate),
                            descriptionInputErrorMessage = null,
                        ),
                        AddTaskViewState.Submitting(
                            taskInput = taskInput,
                        ),
                        AddTaskViewState.Active(
                            taskInput = taskInput,
                            descriptionInputErrorMessage = UIText.ResourceText(R.string.err_empty_task_description),
                        ),
                    ),
                )
        }
}
