package com.onepercentbetter.tasklist.ui

import com.onepercentbetter.CoroutinesTestRule
import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.tasklist.domain.model.Task
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class TaskListViewModelTest {

    private val testRobot = TaskListViewModelRobot()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun successfulLoad() = runTest {
        val task = Task(
            description = "Test task"
        )

        val tasksResult = Result.Success(
            listOf(task)
        )

        testRobot
            .mockAllTasksResult(tasksResult)
            .buildViewModel()
            .expectedViewStates(
                action = {},
                viewStates = listOf<TaskListViewState>(
                    TaskListViewState.Loading,
                    TaskListViewState.Loaded(listOf(task))
                )
            )
    }

    @Test
    fun failureLoad() = runTest {
        val tasksResult: Result<List<Task>> = Result.Error(Throwable("Whoops"))

        testRobot
            .mockAllTasksResult(tasksResult)
            .buildViewModel()
            .expectedViewStates(
                action = {},
                viewStates = listOf<TaskListViewState>(
                    TaskListViewState.Loading,
                    TaskListViewState.Error(
                        errorMessage = UIText.StringText("Something went wrong! ;(")
                    )
                )
            )
    }
}
