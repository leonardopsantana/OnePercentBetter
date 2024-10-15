package com.onepercentbetter.addtask.ui

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.fakes.FakeAddTaskUseCase
import io.mockk.every
import io.mockk.mockk
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlinx.coroutines.test.TestDispatcher

class AddTaskViewModelRobot {
    private val fakeAddTaskUseCase = FakeAddTaskUseCase()
    private val mockSaveStateHandle: SavedStateHandle = mockk(relaxed = true)
    private lateinit var viewModel: AddTaskViewModel

    fun buildViewModel(dispatcher: TestDispatcher) =
        apply {
            viewModel =
                AddTaskViewModel(
                    addTaskUseCase = fakeAddTaskUseCase.mock,
                    savedStateHandle = mockSaveStateHandle,
                    ioDispatcher = dispatcher
                )
        }

    fun mockInitialDate(
        date: LocalDate,
    ) = apply {
        every {
            mockSaveStateHandle.get<LocalDate?>("initDate")
        } returns date
    }

    fun mockResultForTask(
        task: Task,
        result: AddTaskResult,
    ) =
        apply {
            fakeAddTaskUseCase.mockResultForTask(task, result)
        }

    fun enterDescription(
        newDescription: String,
    ) = apply {
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

    fun assertViewState(
        expectedViewState: AddTaskViewState,
    ) = apply {
        viewModel.viewState.value.let {
            assertThat(it).isEqualTo(expectedViewState)
        }
    }
}
