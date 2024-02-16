package com.onepercentbetter.addtask.ui

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.fakes.FakeAddTestUseCase
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
        result: AddTaskResult
    ) = apply {
        fakeAddTaskUseCase.mockResultForTask(result)
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
