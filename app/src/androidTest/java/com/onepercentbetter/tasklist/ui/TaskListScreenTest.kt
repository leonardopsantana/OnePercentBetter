package com.onepercentbetter.tasklist.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.onepercentbetter.FakeDestinationsNavigator
import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core.ui.components.WindowSize
import com.onepercentbetter.destinations.AddTaskDialogDestination
import com.onepercentbetter.destinations.AddTaskScreenDestination
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
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
        val markTaskAsCompleteUseCase = FakeMarkTaskAsCompleteUseCase()

        getTasksForDateUseCase.mockResultForDate(
            date = LocalDate.now(),
            result = flowOf(Result.Success(emptyList()))
        )

        val viewModel = TaskListViewModel(
            getTasksForDateUseCase = getTasksForDateUseCase,
            markTaskAsCompleteUseCase = markTaskAsCompleteUseCase
        )

        val destinationsNavigator = FakeDestinationsNavigator()

        composeTestRule.setContent {
            TaskListScreen(
                navigator = destinationsNavigator,
                viewModel = viewModel,
                windowSize = WindowSize.Compact
            )
        }

        composeTestRule
            .onNodeWithTag(ADD_TASK_BUTTON_TAG)
            .performClick()

        destinationsNavigator.verifyNavigatedToRoute(
            expectedRoute = AddTaskScreenDestination.route
        )
    }

    @Test
    fun navigateToAddTaskScreenForExpandedWindowSize() {
        val getTasksForDateUseCase = FakeGetTasksForDateUseCase()
        val markTaskAsCompleteUseCase = FakeMarkTaskAsCompleteUseCase()

        getTasksForDateUseCase.mockResultForDate(
            date = LocalDate.now(),
            result = flowOf(Result.Success(emptyList()))
        )

        val viewModel = TaskListViewModel(
            getTasksForDateUseCase = getTasksForDateUseCase,
            markTaskAsCompleteUseCase = markTaskAsCompleteUseCase
        )

        val destinationsNavigator = FakeDestinationsNavigator()

        composeTestRule.setContent {
            TaskListScreen(
                navigator = destinationsNavigator,
                viewModel = viewModel,
                windowSize = WindowSize.Expanded
            )
        }

        composeTestRule
            .onNodeWithTag(ADD_TASK_BUTTON_TAG)
            .performClick()

        destinationsNavigator.verifyNavigatedToRoute(
            expectedRoute = AddTaskDialogDestination.route
        )
    }
}
