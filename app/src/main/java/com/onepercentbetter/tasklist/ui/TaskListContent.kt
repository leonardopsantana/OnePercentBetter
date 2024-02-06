package com.onepercentbetter.tasklist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.onepercentbetter.tasklist.domain.model.Task
import java.time.LocalDate

@Composable
fun TaskListContent(
    viewState: TaskListViewState,
    onRescheduleClicked: (Task) -> Unit,
    onDoneClicked: (Task) -> Unit,
    onAddButtonClicked: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (viewState) {
            is TaskListViewState.Error -> {
                // coming soon
            }

            is TaskListViewState.Loaded -> {
                LoadedTasksContent(
                    viewState,
                    onAddButtonClicked,
                    onRescheduleClicked,
                    onDoneClicked
                )
            }

            TaskListViewState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun LoadedTasksContent(
    viewState: TaskListViewState.Loaded,
    onAddButtonClicked: () -> Unit,
    onRescheduleClicked: (Task) -> Unit,
    onDoneClicked: (Task) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            AddTaskButton(onAddButtonClicked)
        },
        topBar = {
            TaskListToolbar()
        },
    ) { paddingValues ->
        TaskList(
            tasks = viewState.tasks,
            onRescheduleClicked = onRescheduleClicked,
            onDoneClicked = onDoneClicked,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun TaskListToolbar() {
    val toolbarHeight = 84.dp

    Surface(
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .statusBarsPadding()
                .height(84.dp)
        ) {
            ToolbarIconButton(
                icon = Icons.Default.KeyboardArrowLeft,
                onClick = {},
                contentDescription = "TODO",
                toolbarHeight = toolbarHeight
            )

            Text(
                text = "Today",
                modifier = Modifier.weight(1F),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            ToolbarIconButton(
                icon = Icons.Default.KeyboardArrowRight,
                onClick = {},
                contentDescription = "TODO",
                toolbarHeight = toolbarHeight
            )
        }
    }
}

@Composable
private fun ToolbarIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String,
    toolbarHeight: Dp
) {
    IconButton(onClick = onClick) {
        Icon(
            icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(toolbarHeight)
        )
    }
}

@Composable
private fun AddTaskButton(onAddButtonClicked: () -> Unit) {
    FloatingActionButton(
        onClick = onAddButtonClicked,
        shape = CircleShape,
        modifier = Modifier.navigationBarsPadding()
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = stringResource(R.string.add_task)
        )
    }
}

@Preview(
    name = "Night mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun TaskListContentPreview() {
    val tasks = (1..10).map { index ->
        Task(
            id = "$index",
            description = "Test task: $index",
            scheduledDate = LocalDate.now()
        )
    }

    val viewState = TaskListViewState.Loaded(tasks)

    OPBTheme {
        TaskListContent(viewState, {}, {}, {})
    }
}
