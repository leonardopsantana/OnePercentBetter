package com.onepercentbetter.addtask.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * A container for the Add Task screen. Unlike [AddTaskContent], this is a stateful composable
 * that can consume a [viewModel] and a [navigator] to handle navigation.
 *
 * This is used to share code that appears in two destinations, the [AddTaskScreen] and
 * the [AddTaskDialog].
 */
@Composable
fun AddTaskContainer(
    viewModel: AddTaskViewModel,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier
) {
    val viewState = viewModel.viewState.collectAsState()

    DisposableEffect(viewState.value) {
        if (viewState.value is AddTaskViewState.Completed) {
            navigator.popBackStack()
        }

        onDispose { }
    }

    AddTaskContent(
        viewState = viewState.value,
        onTaskDescriptionChanged = viewModel::onTaskDescriptionChanged,
        onTaskScheduleDateChanged = viewModel::onTaskScheduleDateChanged,
        onSubmitClicked = viewModel::onSubmitButtonClicked,
        modifier = modifier
    )
}
