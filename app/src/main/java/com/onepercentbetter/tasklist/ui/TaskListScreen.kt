package com.onepercentbetter.tasklist.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.onepercentbetter.destinations.AddTaskDialogDestination
import com.onepercentbetter.destinations.AddTaskScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    start = true
)
@Composable
fun TaskListScreen(
    navigator: DestinationsNavigator,
    viewModel: TaskListViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()

    Surface {
        TaskListContent(
            viewState = viewState.value,
            onRescheduleClicked = {},
            onDoneClicked = viewModel::onDoneButtonClicked,
            onAddButtonClicked = {
                val destination = if (true) {
                    AddTaskScreenDestination
                } else {
                    AddTaskDialogDestination
                }
                navigator.navigate(destination)
            },
            onPreviousDateButtonClicked = viewModel::onPreviousDateButtonClicked,
            onNextDateButtonClicked = viewModel::onNextDateButtonClicked,
        )
    }
}
