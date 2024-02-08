package com.onepercentbetter.tasklist.ui

import com.onepercentbetter.CoroutinesTestRule
import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.tasklist.domain.model.Task
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class TaskListViewModelTest {

    private val testRobot = TaskListViewModelRobot()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun successfulLoad() = runTest {
        val task = Task(
            id = "TEST ID",
            description = "Test task",
            scheduledDate = LocalDate.now()
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
                        tasks = null,
                        showLoading = true
                    ),
                    TaskListViewState(
                        tasks = listOf(task),
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
                        tasks = null,
                        showLoading = true
                    ),
                    TaskListViewState(
                        errorMessage = UIText.StringText("Something went wrong! ;(")
                    )
                )
            )
    }
}
