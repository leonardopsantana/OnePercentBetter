package com.onepercentbetter.tasklist.ui

import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.onepercentbetter.addtask.ui.AddTaskNavArguments
import com.onepercentbetter.destinations.AddTaskDialogDestination
import com.onepercentbetter.destinations.AddTaskScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun TaskListScreen(
    navigator: DestinationsNavigator,
    windowWidthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    viewModel: TaskListViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()

    Surface {
        TaskListContent(
            viewState = viewState.value,
            onRescheduleClicked = viewModel::onRescheduleButtonClicked,
            onDoneClicked = viewModel::onDoneButtonClicked,
            onAddButtonClicked = {
                val navArgs = AddTaskNavArguments(
                    initDate = viewState.value.selectedDate,
                )
                val destination =
                    if (windowWidthSizeClass != WindowWidthSizeClass.Compact) {
                        AddTaskDialogDestination(
                            navArgs.initDate,
                        )
                    } else {
                        AddTaskScreenDestination(
                            navArgs.initDate,
                        )
                    }
                navigator.navigate(destination)
            },
            onDateSelected = viewModel::onDateSelected,
            onTaskRescheduled = viewModel::onTaskRescheduled,
            onReschedulingCompleted = viewModel::onReschedulingCompleted,
            onAlertMessageShown = viewModel::onAlertMessageShown,
            modifier = Modifier
                .testTag(TaskListScreen.TEST_TAG),
        )
    }
}

object TaskListScreen {
    const val TEST_TAG = "TASK_LIST_SCREEN"
}
