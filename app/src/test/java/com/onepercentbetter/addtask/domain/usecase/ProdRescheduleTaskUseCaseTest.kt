package com.onepercentbetter.addtask.domain.usecase

import com.onepercentbetter.core.model.Task
import com.onepercentbetter.tasklist.domain.usecases.ProdRescheduleTaskForDateUseCase
import com.onepercentbetter.toEpochMillis
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate

class ProdRescheduleTaskUseCaseTest {

    private val fakeRepository = com.onepercentbetter.fakes.FakeTaskRepository()
    private val useCase = ProdRescheduleTaskForDateUseCase(
        taskRepository = fakeRepository
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun rescheduleTask() = runTest {
        val initialTask = Task(
            id = "TestId",
            description = "Test task",
            scheduledDateMillis = 0L,
            completed = true
        )

        val newDate = LocalDate.now().plusDays(1)

        val expectedNewTask = initialTask.copy(
            scheduledDateMillis = newDate.toEpochMillis(),
        )

        fakeRepository.updateTaskResults[expectedNewTask] = Result.success(Unit)

        useCase.invoke(initialTask, newDate)
    }



}
