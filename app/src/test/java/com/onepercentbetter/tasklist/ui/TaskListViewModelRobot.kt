package com.onepercentbetter.tasklist.ui

import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.fakes.FakeGetTasksForDateUseCase
import com.onepercentbetter.fakes.FakeRescheduleTaskUseCase
import com.onepercentbetter.fakes.FakeTaskRepository
import com.onepercentbetter.task.api.TaskListResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TaskListViewModelRobot {
    private val fakeGetTasksForDateUseCase = FakeGetTasksForDateUseCase()
    private val fakeTaskRepository = FakeTaskRepository()
    private val fakeRescheduleTaskUseCase = FakeRescheduleTaskUseCase()
    private lateinit var viewModel: TaskListViewModel

    fun buildViewModel() =
        apply {
            viewModel =
                TaskListViewModel(
                    getTasksForDateUseCase = fakeGetTasksForDateUseCase,
                    taskRepository = fakeTaskRepository,
                    rescheduleTaskUseCase = fakeRescheduleTaskUseCase
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

    fun clickRescheduleButton(task: Task) = apply {
        viewModel.onRescheduleButtonClicked(task)
    }

    fun rescheduleTaskForDate(
        task: Task,
        date: LocalDate
    ) = apply {
        viewModel.onTaskRescheduled(task, date)
    }

    fun assertTaskRescheduleForDate(
        task: Task,
        date: LocalDate
    ) = apply {
        fakeRescheduleTaskUseCase.assertInvocation(
            task to date
        )
    }

    /**
     * Look up and dismiss first alert message ID
     */
    fun showAlertMessage() = apply {
        viewModel.viewState.value.alertMessages.firstOrNull()?.id?.let { id ->
            viewModel.onAlertMessageShown(id)
        }
    }

    fun dismissAlertMessage() = apply {
        viewModel.viewState.value.alertMessages?.firstOrNull()?.onDismissed?.invoke()
    }
}
