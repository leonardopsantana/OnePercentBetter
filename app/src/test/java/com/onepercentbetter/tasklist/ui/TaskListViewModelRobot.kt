package com.onepercentbetter.tasklist.ui

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.fakes.FakeGetTasksForDateUseCase
import com.onepercentbetter.fakes.FakeRescheduleTaskUseCase
import com.onepercentbetter.task.api.TaskListResult
import com.onepercentbetter.task.api.test.FakeTaskRepository
import com.onepercentbetter.tasklist.domain.usecases.ProdMarkTaskAsCompletedUseCase
import com.onepercentbetter.tasklist.domain.usecases.ProdRescheduleTaskForDateUseCase
import kotlinx.coroutines.flow.Flow
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
                    rescheduleTaskUseCase = FakeRescheduleTaskUseCase()
                )
        }

    fun assertViewState(expectedViewState: TaskListViewState) =
        apply {
            val actualViewState = viewModel.viewState.value
            assertThat(actualViewState).isEqualTo(expectedViewState)
        }

    fun mockTaskListResultForDate(
        date: LocalDate,
        result: Flow<TaskListResult>,
    ) = apply {
        fakeGetTasksForDateUseCase.mockResultForDate(date, result)
    }

    fun clickPreviousDateButton() =
        apply {
            viewModel.onPreviousDateButtonClicked()
        }

    fun clickNextDateButton() =
        apply {
            viewModel.onNextDateButtonClicked()
        }

    fun clickRescheduleButton(task: Task) = apply {
        viewModel.onRescheduleButtonClicked(task)
    }

    fun rescheduleTaskForDate(
        task: Task,
        date: LocalDate
    ) = apply {
        viewModel.onTaskRescheduled(task, date)
    }
}
