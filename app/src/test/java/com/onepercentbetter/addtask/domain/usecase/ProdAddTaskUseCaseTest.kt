package com.onepercentbetter.addtask.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.core.model.Task
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class ProdAddTaskUseCaseTest {
    private val fakeTaskRepository = com.onepercentbetter.fakes.FakeTaskRepository()

    private val useCase =
        ProdAddTaskUseCase(
            taskRepository = fakeTaskRepository,
        )

    @Test
    fun submitWithEmptyDescription() =
        runTest {
            val taskToSubmit =
                Task(
                    id = "Testing",
                    description = "",
                    scheduledDateMillis =
                        ZonedDateTime.now()
                            .toInstant()
                            .toEpochMilli(),
                    completed = false,
                )

            val expectedResult =
                AddTaskResult.Failure.InvalidInput(
                    emptyDescription = true,
                    scheduledDateInPast = false,
                )

            val actualResult = useCase.invoke(taskToSubmit)

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun submitWithBlankDescription() =
        runTest {
            val taskToSubmit =
                Task(
                    id = "Testing",
                    description = "    ",
                    scheduledDateMillis =
                        ZonedDateTime.now()
                            .toInstant()
                            .toEpochMilli(),
                    completed = false,
                )

            val expectedResult =
                AddTaskResult.Failure.InvalidInput(
                    emptyDescription = true,
                    scheduledDateInPast = false,
                )

            val actualResult = useCase.invoke(taskToSubmit)

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun submitWithScheduledDateInPast() =
        runTest {
            val taskToSubmit =
                Task(
                    id = "Testing",
                    description = "Some description",
                    scheduledDateMillis =
                        LocalDate.now().minusDays(1)
                            .atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli(),
                    completed = false,
                )

            val expectedResult =
                AddTaskResult.Failure.InvalidInput(
                    emptyDescription = false,
                    scheduledDateInPast = true,
                )

            val actualResult = useCase.invoke(taskToSubmit)
            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun submitValidTaskWithExtraWhitespace() =
        runTest {
            val inputTask =
                Task(
                    id = "Some ID",
                    description = "   Testing      ",
                    scheduledDateMillis =
                        ZonedDateTime.now()
                            .toInstant()
                            .toEpochMilli(),
                    completed = false,
                )

            val expectedSavedTask =
                inputTask.copy(
                    description = "Testing",
                )

            fakeTaskRepository.addTasksResults[expectedSavedTask] = Result.success(Unit)

            val expectedResult = AddTaskResult.Success
            val actualResult = useCase.invoke(inputTask)
            assertThat(actualResult).isEqualTo(expectedResult)
        }
}
