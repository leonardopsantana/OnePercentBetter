package com.onepercentbetter.addtask.ui

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.fakes.FakeAddTaskUseCase
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class AddTaskViewModelRobot {
    private val fakeAddTaskUseCase = FakeAddTaskUseCase()
    private lateinit var viewModel: AddTaskViewModel

    fun buildViewModel() =
        apply {
            viewModel =
                AddTaskViewModel(
                    addTaskUseCase = fakeAddTaskUseCase.mock,
                )
        }

    fun mockResultForTask(result: AddTaskResult) =
        apply {
            fakeAddTaskUseCase.mockResultForTask(result)
        }

    fun enterDescription(newDescription: String) =
        apply {
            viewModel.onTaskDescriptionChanged(newDescription)
        }

    fun selectDate(
        newScheduledDate: Long,
    ) = apply {
        val scheduledDate = Instant.ofEpochMilli(newScheduledDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        viewModel.onTaskScheduleDateChanged(scheduledDate)
    }

    fun clickSubmit() =
        apply {
            viewModel.onSubmitButtonClicked()
        }

    fun assertViewState(expectedViewState: AddTaskViewState) =
        apply {
            val actualViewState = viewModel.viewState.value
            assertThat(actualViewState).isEqualTo(expectedViewState)
        }
}
