package com.onepercentbetter.addtask.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.compose.ui.Modifier
import com.onepercentbetter.R

/**
 * This is used when, when we want to shown an individual screen that allows
 * the user to create a new task.
 */
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun AddTaskScreen(
    navigator: DestinationsNavigator,
    viewModel: AddTaskViewModel = hiltViewModel(),
) {
    AddTaskContainer(
        viewModel = viewModel,
        navigator = navigator,
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.screen_padding))
            .statusBarsPadding(),
    )
}


