package com.onepercentbetter.addtask.ui

import com.onepercentbetter.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class AddTaskViewModelTest {
    private val testRobot = AddTaskViewModelRobot()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Test
    fun createWithInitialDateFromSaveStateHandle() {
        val initialDate = LocalDate.now().plusDays(1)

        val expectedViewState = AddTaskViewState.Initial(initialDate)

        testRobot
            .mockInitialDate(initialDate)
            .buildViewModel()
            .assertViewState(expectedViewState)
    }

//    @Test
//    fun submitWithEmptyDescription() {
//        val taskToSubmit = Task(
//            id = "Testing",
//            description = "",
//            scheduledDateMillis = 0L,
//            completed = false,
//        )
//
//        val useCaseResult = AddTaskResult.Failure.InvalidInput(
//            emptyDescription = true,
//            scheduledDateInPast = false,
//        )
//
//        val expectedViewState = AddTaskViewState.Active(
//            taskInput = TaskInput(
//                description = taskToSubmit.description,
//                scheduledDate = Instant.ofEpochMilli(taskToSubmit.scheduledDateMillis)
//                    .atZone(ZoneId.systemDefault())
//                    .toLocalDate(),
//            ),
//            descriptionInputErrorMessage = UIText.ResourceText(R.string.err_empty_task_description),
//            scheduledDateInputErrorMessage = null,
//        )
//    }
}
