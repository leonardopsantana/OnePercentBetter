package com.onepercentbetter.addtask.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.fakes.FakeTaskRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate

class ProdAddTaskUseCaseTest {

    private val fakeTaskRepository = FakeTaskRepository()

    private val useCase = ProdAddTaskUseCase(
        taskRepository = fakeTaskRepository.mock
    )

    @Test
    fun submitWithEmptyDescription() = runTest {
        val taskToSubmit = com.onepercentbetter.core_model.Task(
            id = "Testing",
            description = "",
            scheduledDateMillis = LocalDate.now(),
            completed = false
        )

        val expectedResult = AddTaskResult.Failure.InvalidInput(
            emptyDescription = true,
            scheduledDateInPast = false
        )

        val actualResult = useCase.invoke(taskToSubmit)

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun submitWithEmptyDate() = runTest {
        val taskToSubmit = com.onepercentbetter.core_model.Task(
            id = "Testing",
            description = "Task test",
            scheduledDateMillis = LocalDate.now().minusDays(1),
            completed = false
        )

        val expectedResult = AddTaskResult.Failure.InvalidInput(
            emptyDescription = false,
            scheduledDateInPast = true
        )

        val actualResult = useCase.invoke(taskToSubmit)

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
