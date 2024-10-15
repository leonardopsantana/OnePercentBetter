package com.onepercentbetter.addtask.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.fakes.FakePreferences
import com.onepercentbetter.fakes.FakeTaskRepository
import com.onepercentbetter.core.datastore.UserPreferences
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class AddTaskUseCaseImplTest {
    private val fakeTaskRepository = FakeTaskRepository()
    private val userPreferences = com.onepercentbetter.core.datastore.UserPreferences(
        preferences = FakePreferences(),
    )

    private val useCase =
        AddTaskUseCaseImpl(
            taskRepository = fakeTaskRepository,
            userPreferences = userPreferences,
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

//            val actualResult = useCase.invoke(taskToSubmit, userPreferences)
//
//            assertThat(actualResult).isEqualTo(expectedResult)
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

//            val actualResult = useCase.invoke(taskToSubmit)
//
//            assertThat(actualResult).isEqualTo(expectedResult)
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

//            val actualResult = withAll(fakeTaskRepository, userPreferences) {
//                addTask(
//                    task = taskToSubmit,
//                    ignoreTaskLimits = false,
//                )
//            }
//            assertThat(actualResult).isEqualTo(expectedResult)
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

//            fakeTaskRepository.addTasksResults[expectedSavedTask] = Result.success(Unit)
//
//            val expectedResult = AddTaskResult.Success
//            val actualResult = useCase.invoke(inputTask)
//            assertThat(actualResult).isEqualTo(expectedResult)
        }

    /**
     * If our preferences say we have a limit on tasks,
     * but the preference is disabled, ensure that we ignore this limit.
     */
    @Test
    fun submitWithPreferenceLimitButDisabled() =
        runTest {
            userPreferences.setPreferredNumTasksPerDay(0)
            userPreferences.setPrefferedNumTasksPerDayEnabled(false)

            val taskToSubmit = Task(
                id = "Testing",
                description = "Test",
                scheduledDateMillis = ZonedDateTime.now()
                    .toInstant()
                    .toEpochMilli(),
                completed = false,
            )

            // Mock a result for this task specifically
            // we do this to ensure in our test that the insert function is ultimately
            // called with the task that we expect it to be.
            fakeTaskRepository.addTaskResults[taskToSubmit] = Result.success(Unit)

            val expectedResult = AddTaskResult.Success

            val actualResult = useCase.invoke(
                task = taskToSubmit,
                ignoreTaskLimits = false,
            )

            assertThat(actualResult).isEqualTo(expectedResult)
        }
}
