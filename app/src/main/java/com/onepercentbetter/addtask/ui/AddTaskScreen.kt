package com.onepercentbetter.addtask.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.onepercentbetter.R

@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun AddTaskScreen(
    viewModel: AddTaskViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()

    Surface {
        AddTaskContent(
            viewState = viewState.value,
            onTaskDescriptionChanged = {},
            onTaskScheduleDateChanged = {},
            onSubmitClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.screen_padding))
                .statusBarsPadding()
        )
    }
}
