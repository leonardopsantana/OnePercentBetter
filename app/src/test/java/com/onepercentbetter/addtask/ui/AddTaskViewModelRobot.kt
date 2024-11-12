package com.onepercentbetter.addtask.ui

import androidx.lifecycle.SavedStateHandle
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.fakes.FakeAddTaskUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.TestDispatcher
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class AddTaskViewModelRobot {
    private var addTaskUseCase = FakeAddTaskUseCase()
    private val mockSaveStateHandle: SavedStateHandle = mockk(relaxed = true)
    lateinit var viewModel: AddTaskViewModel

    fun buildViewModel(
        dispatcher: TestDispatcher,
    ) = apply {
        viewModel =
            AddTaskViewModel(
                addTaskUseCase = addTaskUseCase.mock,
                savedStateHandle = mockSaveStateHandle,
                ioDispatcher = dispatcher,
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
    ) = apply {
        addTaskUseCase.mockResultForTask(task, result)
    }

    fun enterDescription(
        newDescription: String,
    ) = apply {
        viewModel.onTaskDescriptionChanged(newDescription)
    }

    fun selectDate(
        newScheduledDate: Long,
    ) = apply {
        val scheduledDate = Instant
            .ofEpochMilli(newScheduledDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        viewModel.onTaskScheduleDateChanged(scheduledDate)
    }

    fun clickSubmit(
        task: Task,
    ) = apply {
        viewModel.onSubmitButtonClicked(task)
    }
}
