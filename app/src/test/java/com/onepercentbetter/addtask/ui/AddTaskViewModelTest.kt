package com.onepercentbetter.addtask.ui

import com.onepercentbetter.InstantTaskCoroutinesExecutorRule
import com.onepercentbetter.addtask.domain.model.TaskInput
import com.onepercentbetter.core.model.Task
import java.time.Instant
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import kotlinx.coroutines.test.runTest
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.onepercentbetter.R
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.core.model.toEpochMillis
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AddTaskViewModelTest {
    private val testRobot = AddTaskViewModelRobot()

    @get:Rule
    val executorRule = InstantTaskCoroutinesExecutorRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun createWithInitialDateFromSaveStateHandle() {
        val initialDate = LocalDate.now().plusDays(1)

        val expectedViewState = AddTaskViewState.Initial(initialDate)

        testRobot
            .mockInitialDate(initialDate)
            .buildViewModel(executorRule.dispatcher)
            .assertViewState(expectedViewState)
    }

    @Test
    fun `GIVEN an empty description, WHEN adding a task THEN do not allow it`() {
        runTest {
            val now = LocalDate.now()

            val taskToSubmit = Task(
                id = "Testing",
                description = "",
                scheduledDateMillis = now.toEpochMillis(),
                completed = false,
            )

            testRobot
                .mockInitialDate(now)
                .buildViewModel(executorRule.dispatcher)
                .mockResultForTask(
                    taskToSubmit,
                    AddTaskResult.Failure.InvalidInput(
                        emptyDescription = true,
                        scheduledDateInPast = false
                    )
                )
                .enterDescription(taskToSubmit.description)
                .assertViewState(
                    AddTaskViewState.Active(
                        taskInput = TaskInput(
                            description = taskToSubmit.description,
                            scheduledDate = Instant.ofEpochMilli(taskToSubmit.scheduledDateMillis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate(),
                        ),
                        descriptionInputErrorMessage = null,
                    ),
                )
                .selectDate(taskToSubmit.scheduledDateMillis)
                .assertViewState(
                    AddTaskViewState.Active(
                        taskInput = TaskInput(
                            description = taskToSubmit.description,
                            scheduledDate = Instant.ofEpochMilli(taskToSubmit.scheduledDateMillis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate(),
                        ),
                        descriptionInputErrorMessage = null,
                    ),
                )
                .clickSubmit()
                .assertViewState(AddTaskViewState.Active(
                    taskInput = TaskInput(
                        description = taskToSubmit.description,
                        scheduledDate = Instant.ofEpochMilli(taskToSubmit.scheduledDateMillis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate(),
                    ),
                    descriptionInputErrorMessage = UIText.ResourceText(R.string.err_empty_task_description),
                ))
        }
    }
}
