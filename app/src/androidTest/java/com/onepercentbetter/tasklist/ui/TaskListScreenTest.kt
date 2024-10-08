package com.onepercentbetter.tasklist.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.onepercentbetter.destinations.AddTaskDialogDestination
import com.onepercentbetter.destinations.AddTaskScreenDestination
import com.onepercentbetter.fakes.FakeDestinationsNavigator
import com.onepercentbetter.fakes.FakeRescheduleTaskUseCase
import com.onepercentbetter.fakes.FakeTaskRepository
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class TaskListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navigateToAddTaskScreenForCompactWindowSize() {
        val getTasksForDateUseCase = FakeGetTasksForDateUseCase()
        val fakeTaskRepository = FakeTaskRepository()
        val rescheduleTaskUseCase = FakeRescheduleTaskUseCase()

        getTasksForDateUseCase.mockResultForDate(
            date = LocalDate.now(),
            result = flowOf(Result.success(emptyList())),
        )

        getTasksForDateUseCase.mockResultForDate(
            date = LocalDate.now().plusDays(1),
            result = flowOf(Result.success(emptyList())),
        )

        val viewModel = TaskListViewModel(
            getTasksForDateUseCase = getTasksForDateUseCase,
            rescheduleTaskUseCase = rescheduleTaskUseCase,
            taskRepository = fakeTaskRepository,
        )

        val destinationsNavigator = FakeDestinationsNavigator()

        composeTestRule.setContent {
            TaskListScreen(
                navigator = destinationsNavigator,
                viewModel = viewModel,
                windowWidthSizeClass = WindowWidthSizeClass.Compact,
            )
        }

        composeTestRule
            .onNodeWithTag(ADD_TASK_BUTTON_TAG)
            .performClick()

        destinationsNavigator.verifyNavigatedToDirection(
            expectedDirection = AddTaskScreenDestination(
                initDate = LocalDate.now(),
            ),
        )

        composeTestRule
            .onNodeWithTag(NEXT_DAY_BUTTON_TAG)
            .performClick()
    }

    @Test
    fun navigateToAddTaskScreenForExpandedWindowSize() {
        val getTasksForDateUseCase = FakeGetTasksForDateUseCase()
        val fakeTaskRepository = FakeTaskRepository()
        val rescheduleTaskUseCase = FakeRescheduleTaskUseCase()

        getTasksForDateUseCase.mockResultForDate(
            date = LocalDate.now(),
            result = flowOf(Result.success(emptyList())),
        )

        val viewModel = TaskListViewModel(
            getTasksForDateUseCase = getTasksForDateUseCase,
            rescheduleTaskUseCase = rescheduleTaskUseCase,
            taskRepository = fakeTaskRepository,
        )

        val destinationsNavigator = FakeDestinationsNavigator()

        composeTestRule.setContent {
            TaskListScreen(
                navigator = destinationsNavigator,
                viewModel = viewModel,
                windowWidthSizeClass = WindowWidthSizeClass.Expanded,
            )
        }

        composeTestRule
            .onNodeWithTag(ADD_TASK_BUTTON_TAG)
            .performClick()

        destinationsNavigator.verifyNavigatedToDirection(
            expectedDirection = AddTaskDialogDestination(
                initDate = LocalDate.now(),
            ),
        )
    }
}
