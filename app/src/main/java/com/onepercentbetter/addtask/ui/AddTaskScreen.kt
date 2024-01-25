package com.onepercentbetter.addtask.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.onepercentbetter.R
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun AddTaskScreen(
    navigator: DestinationsNavigator,
    viewModel: AddTaskViewModel = hiltViewModel(),
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
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.screen_padding))
            .statusBarsPadding()
    )
}
