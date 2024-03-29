package com.onepercentbetter.tasklist.ui

import com.onepercentbetter.CoroutinesTestRule
import com.onepercentbetter.core_data.Result
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.core_model.Task
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.ZonedDateTime

class TaskListViewModelTest {

    private val testRobot = TaskListViewModelRobot()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun successfulLoad() = runTest {
        val task = Task(
            id = "TEST ID",
            description = "Test task",
            scheduledDateMillis = ZonedDateTime.now()
                .toInstant()
                .toEpochMilli(),
            completed = false
        )

        val tasksResult = Result.Success(
            listOf(task)
        )

        testRobot
            .mockTasksForDateResult(LocalDate.now(), tasksResult)
            .buildViewModel()
            .expectedViewStates(
                action = {},
                viewStates = listOf(
                    TaskListViewState(
                        incompleteTasks = null,
                        completedTasks = null,
                        showLoading = true
                    ),
                    TaskListViewState(
                        incompleteTasks = listOf(task),
                        completedTasks = emptyList(),
                        showLoading = false
                    ),
                    TaskListViewState(
                        incompleteTasks = listOf(task),
                        completedTasks = listOf(task),
                        showLoading = false
                    )
                )
            )
    }

    @Test
    fun clickPreviousDate() = runTest {
        val task = Task(
            id = "TEST ID",
            description = "Test task",
            scheduledDateMillis = ZonedDateTime.now()
                .toInstant()
                .toEpochMilli(),
            completed = false
        )

        val taskList = listOf(task)

        val tasksResult = Result.Success(
            taskList
        )

        testRobot
            .mockTasksForDateResult(
                date = LocalDate.now().minusDays(1),
                result = tasksResult
            )
            .buildViewModel()
            .expectedViewStates(
                action = {
                    clickPreviousDateButton()
                },
                viewStates = listOf(
                    TaskListViewState(
                        incompleteTasks = null,
                        completedTasks = null,
                        showLoading = true
                    ),
                    TaskListViewState(
                        incompleteTasks = null,
                        completedTasks = emptyList(),
                        showLoading = true
                    ),
                    TaskListViewState(
                        selectedDate = LocalDate.now().minusDays(1),
                        incompleteTasks = emptyList(),
                        completedTasks = emptyList(),
                        showLoading = true
                    ),
                    TaskListViewState(
                        selectedDate = LocalDate.now().minusDays(1),
                        incompleteTasks = taskList,
                        completedTasks = emptyList(),
                        showLoading = false
                    ),
                    TaskListViewState(
                        selectedDate = LocalDate.now().minusDays(1),
                        incompleteTasks = taskList,
                        completedTasks = taskList,
                        showLoading = false
                    ),
                )
            )
    }

    @Test
    fun clickNextDate() = runTest {
        val task = Task(
            id = "TEST ID",
            description = "Test task",
            scheduledDateMillis = ZonedDateTime.now()
                .toInstant()
                .toEpochMilli(),
            completed = false
        )

        val taskList = listOf(task)

        val tasksResult = Result.Success(
            taskList
        )

        testRobot
            .mockTasksForDateResult(
                date = LocalDate.now(),
                result = Result.Success(emptyList())
            )
            .mockTasksForDateResult(
                date = LocalDate.now().plusDays(1),
                result = tasksResult
            )
            .buildViewModel()
            .expectedViewStates(
                action = {
                    clickNextDateButton()
                },
                viewStates = listOf(
                    TaskListViewState(
                        incompleteTasks = null,
                        completedTasks = null,
                        showLoading = true
                    ),
                    TaskListViewState(
                        selectedDate = LocalDate.now().plusDays(1),
                        incompleteTasks = null,
                        completedTasks = null,
                        showLoading = true
                    ),
                    TaskListViewState(
                        selectedDate = LocalDate.now().plusDays(1),
                        incompleteTasks = taskList,
                        completedTasks = null,
                        showLoading = false
                    ),
                    TaskListViewState(
                        selectedDate = LocalDate.now().plusDays(1),
                        incompleteTasks = taskList,
                        completedTasks = taskList,
                        showLoading = false
                    )
                )
            )
    }

    @Test
    fun failureLoad() = runTest {
        val tasksResult: Result<List<Task>> = Result.Error(Throwable("Whoops"))

        testRobot
            .mockTasksForDateResult(LocalDate.now(), tasksResult)
            .buildViewModel()
            .expectedViewStates(
                action = {},
                viewStates = listOf<TaskListViewState>(
                    TaskListViewState(
                        incompleteTasks = null,
                        completedTasks = null,
                        showLoading = true
                    ),
                    TaskListViewState(
                        incompleteTasks = null,
                        completedTasks = null,
                        errorMessage = UIText.StringText("Something went wrong."),
                        showLoading = false
                    )
                )
            )
    }
}
