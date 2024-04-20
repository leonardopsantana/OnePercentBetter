package com.onepercentbetter.addtask.ui

import com.onepercentbetter.CoroutinesTestRule
import com.onepercentbetter.R
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.addtask.domain.model.TaskInput
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.core.ui.components.UIText
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class AddTaskViewModelTest {
    private val testRobot = AddTaskViewModelRobot()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Test
    fun submitWithEmptyDescription() =
        runTest {
            val taskToSubmit =
                Task(
                    id = "Testing",
                    description = "X",
                    scheduledDateMillis =
                        ZonedDateTime.now()
                            .toInstant()
                            .toEpochMilli(),
                    completed = false,
                )

            val useCaseResult =
                AddTaskResult.Failure.InvalidInput(
                    emptyDescription = true,
                    scheduledDateInPast = false,
                )

            testRobot
                .buildViewModel()
                .mockResultForTask(
                    result = useCaseResult,
                )
                .expectedViewStates(
                    action = {
                        enterDescription(taskToSubmit.description)
                        selectDate(
                            Instant.ofEpochMilli(taskToSubmit.scheduledDateMillis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate(),
                        )
                        clickSubmit()
                    },
                    viewStates =
                        listOf(
                            AddTaskViewState.Initial,
                            AddTaskViewState.Active(TaskInput(description = taskToSubmit.description)),
                            AddTaskViewState.Active(
                                taskInput =
                                    TaskInput(
                                        description = taskToSubmit.description,
                                        scheduledDate =
                                            Instant.ofEpochMilli(taskToSubmit.scheduledDateMillis)
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate(),
                                    ),
                                descriptionInputErrorMessage = UIText.ResourceText(R.string.err_empty_task_description),
                                scheduledDateInputErrorMessage = null,
                            ),
                        ),
                )
        }
}
