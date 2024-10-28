package com.onepercentbetter.addtask.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.core.model.toEpochMillis
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.UUID

/**
 * A container for the Add Task screen. Unlike [AddTaskContent], this is a stateful composable
 * that can consume a [viewModel] and a [navigator] to handle navigation.
 *
 * This is used to share code that appears in two destinations, the [AddTaskScreen] and
 * the [AddTaskDialog].
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddTaskContainer(
    viewModel: AddTaskViewModel,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
) {
    val viewState = viewModel.viewState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    DisposableEffect(viewState.value) {
        if (viewState.value is AddTaskViewState.Completed) {
            keyboardController?.hide()
            navigator.popBackStack()
        }

        onDispose { }
    }

    AddTaskContent(
        viewState = viewState.value,
        onTaskDescriptionChanged = viewModel::onTaskDescriptionChanged,
        onTaskScheduleDateChanged = viewModel::onTaskScheduleDateChanged,
        onSubmitClicked = {
            viewModel.onSubmitButtonClicked(
                Task(
                    id = UUID.randomUUID().toString(),
                    description = viewState.value.taskInput.description,
                    scheduledDateMillis =
                    viewState.value.taskInput.scheduledDate
                        .toEpochMillis(),
                    completed = false,
                ),
            )
        },
        modifier = modifier,
    )
}
