package com.onepercentbetter.addtask.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.fakes.FakeTaskListRepository
import com.onepercentbetter.tasklist.domain.model.Task
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate

class ProdAddTaskUseCaseTest {

    private val fakeTaskListRepository = FakeTaskListRepository()

    private val useCase = ProdAddTaskUseCase(
        taskListRepository = fakeTaskListRepository.mock
    )

    @Test
    fun submitWithEmptyDescription()= runTest {
        val taskToSubmit = Task(
            id = "Testing",
            description = "",
            scheduledDate = LocalDate.now()
        )

        val expectedResult = AddTaskResult.Failure.InvalidInput(
            emptyDescription = true,
            scheduledDateInPast = false
        )

        val actualResult = useCase.invoke(taskToSubmit)

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun submitWithEmptyDate()= runTest {
        val taskToSubmit = Task(
            id = "Testing",
            description = "Task test",
            scheduledDate = LocalDate.now().minusDays(1)
        )

        val expectedResult = AddTaskResult.Failure.InvalidInput(
            emptyDescription = false,
            scheduledDateInPast = true
        )

        val actualResult = useCase.invoke(taskToSubmit)

        assertThat(actualResult).isEqualTo(expectedResult)
    }

}