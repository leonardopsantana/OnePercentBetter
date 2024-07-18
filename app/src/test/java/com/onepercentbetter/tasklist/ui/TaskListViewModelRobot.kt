package com.onepercentbetter.tasklist.ui

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.fakes.FakeGetTasksForDateUseCase
import com.onepercentbetter.task.api.TaskListResult
import com.onepercentbetter.task.api.test.FakeTaskRepository
import com.onepercentbetter.tasklist.domain.usecases.ProdMarkTaskAsCompletedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class TaskListViewModelRobot {
    private val fakeGetTasksForDateUseCase = FakeGetTasksForDateUseCase()
    private val fakeTaskRepository = FakeTaskRepository()
    private lateinit var viewModel: TaskListViewModel

    fun buildViewModel() =
        apply {
            viewModel =
                TaskListViewModel(
                    getTasksForDateUseCase = fakeGetTasksForDateUseCase,
                    markTaskAsCompleteUseCase =
                        ProdMarkTaskAsCompletedUseCase(
                            taskRepository = fakeTaskRepository,
                        ),
                )
        }

    fun mockTaskListResultForDate(
        date: LocalDate,
        result: Flow<TaskListResult>,
    ) = apply {
        fakeGetTasksForDateUseCase.mockResultForDate(date, result)
    }

    fun assertViewState(expectedViewState: TaskListViewState) =
        apply {
            val actualViewState = viewModel.viewState.value
            assertThat(actualViewState).isEqualTo(expectedViewState)
        }

    /**
     * Launch a coroutine that will observe our [viewModel]'s view state and ensure that we consume
     * all of the supplied [viewStates] in the same order that they are supplied.
     *
     * We should call this near the front of the test, to ensure that every view state we emit
     * can be collected by this.
     */
    suspend fun expectedViewStates(
        action: TaskListViewModelRobot.() -> Unit,
        viewStates: List<TaskListViewState>,
    ) = apply {
        viewModel.viewState.test {
            action()

            for (state in viewStates) {
                assertThat(awaitItem()).isEqualTo(state)
            }

            this.cancel()
        }
    }

    fun clickPreviousDateButton() =
        apply {
            viewModel.onPreviousDateButtonClicked()
        }

    fun clickNextDateButton() =
        apply {
            viewModel.onNextDateButtonClicked()
        }
}
