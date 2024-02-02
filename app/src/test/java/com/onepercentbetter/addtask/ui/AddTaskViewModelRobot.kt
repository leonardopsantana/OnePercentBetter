package com.onepercentbetter.addtask.ui

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.fakes.FakeAddTestUseCase
import com.onepercentbetter.login.ui.LoginViewModelRobot
import com.onepercentbetter.login.ui.LoginViewState
import com.onepercentbetter.tasklist.domain.model.Task
import java.time.LocalDate

class AddTaskViewModelRobot {
    private val fakeAddTaskUseCase = FakeAddTestUseCase()
    private lateinit var viewModel: AddTaskViewModel

    fun buildViewModel() = apply {
        viewModel = AddTaskViewModel(
            addTaskUseCase = fakeAddTaskUseCase.mock
        )
    }

    fun mockResultForTask(
        task: Task,
        result: AddTaskResult
    ) = apply {
        fakeAddTaskUseCase.mockResultForTask(task, result)
    }

    fun enterDescription(
        newDescription: String,
    ) = apply {
        viewModel.onTaskDescriptionChanged(newDescription)
    }

    fun selectDate(
        newDate: LocalDate
    ) = apply {
        viewModel.onTaskScheduleDateChanged(newDate)
    }

    fun clickSubmit() = apply {
        viewModel.onSubmitButtonClicked()
    }

    fun assertViewState(
        expectedViewState: AddTaskViewState
    ) = apply {
        val actualViewState = viewModel.viewState.value
        assertThat(actualViewState).isEqualTo(expectedViewState)
    }

    suspend fun expectedViewStates(
        action: AddTaskViewModelRobot.() -> Unit,
        viewStates: List<AddTaskViewState>,
    ) = apply {
        viewModel.viewState.test {
            action()

            for (state in viewStates) {
                assertThat(awaitItem()).isEqualTo(state)
            }

            this.cancel()
        }
    }
}