package com.onepercentbetter.tasklist.ui

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.onepercentbetter.ExcludeFromJacocoGeneratedReport
import com.onepercentbetter.R
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.core.ui.adaptiveWidth
import com.onepercentbetter.core.ui.components.Material3CircularProgressIndicator
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.core.ui.components.getString
import com.onepercentbetter.core.ui.components.md3DatePickerColors
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.onepercentbetter.toEpochMillis
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

const val ADD_TASK_BUTTON_TAG = "ADD_TASK_BUTTON"
const val NEXT_DAY_BUTTON_TAG = "NEXT_DAY_BUTTON"

@OptIn(ExperimentalMaterial3Api::class)
@ExcludeFromJacocoGeneratedReport
@Composable
fun TaskListContent(
    viewState: TaskListViewState,
    onRescheduleClicked: (Task) -> Unit,
    onDoneClicked: (Task) -> Unit,
    onAddButtonClicked: () -> Unit,
    onPreviousDateButtonClicked: () -> Unit,
    onNextDateButtonClicked: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onTaskRescheduled: (Task, LocalDate) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            AddTaskButton(onAddButtonClicked)
        },
        topBar = {
            ToolBarWithDialog(
                onDateSelected,
                viewState,
                onPreviousDateButtonClicked,
                onNextDateButtonClicked
            )
        },
    ) { paddingValues ->
        if (viewState.showTasks) {
            if (viewState.incompleteTasks.isNullOrEmpty() &&
                viewState.completedTasks.isNullOrEmpty()
            ) {
                TaskListEmptyState()
            } else {
                RescheduleTaskDialog(
                    viewState = viewState,
                    onTaskRescheduled = onTaskRescheduled
                )

                TaskList(
                    incompleteTasks = viewState.incompleteTasks.orEmpty(),
                    completedTasks = viewState.completedTasks.orEmpty(),
                    onRescheduleClicked = onRescheduleClicked,
                    onDoneClicked = onDoneClicked,
                    modifier =
                    Modifier
                        .padding(paddingValues)
                        .adaptiveWidth(),
                )
            }
        }

        if (viewState.showLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Material3CircularProgressIndicator(
                    modifier =
                    Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
private fun RescheduleTaskDialog(
    viewState: TaskListViewState,
    onTaskRescheduled: (Task, LocalDate) -> Unit
) {
    val rescheduleTaskDatePickerDialogState = rememberMaterialDialogState()

    LaunchedEffect(viewState) {
        if (viewState.taskToReschedule != null) {
            rescheduleTaskDatePickerDialogState.show()
        }
    }

    MaterialDialog(
        dialogState = rescheduleTaskDatePickerDialogState,
        buttons = {
            positiveButton(stringResource(R.string.ok))
            negativeButton(stringResource(R.string.cancel))
        },
        backgroundColor = MaterialTheme.colorScheme.surface,
    ) {
        this.datepicker(
            colors = md3DatePickerColors(),
            onDateChange = { newDate ->
                viewState.taskToReschedule?.let {
                    onTaskRescheduled.invoke(
                        viewState.taskToReschedule,
                        newDate
                    )
                }
            },
            initialDate = viewState.selectedDate,
        )
    }
}

@Composable
private fun ToolBarWithDialog(
    onDateSelected: (LocalDate) -> Unit,
    viewState: TaskListViewState,
    onPreviousDateButtonClicked: () -> Unit,
    onNextDateButtonClicked: () -> Unit
) {
    val dialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(R.string.ok))
            negativeButton(stringResource(R.string.cancel))
        },
        backgroundColor = MaterialTheme.colorScheme.surface,
    ) {
        this.datepicker(
            colors = md3DatePickerColors(),
            onDateChange = onDateSelected,
            initialDate = viewState.selectedDate,
        )
    }

    TaskListToolbar(
        onLeftButtonClicked = onPreviousDateButtonClicked,
        onRightButtonClicked = onNextDateButtonClicked,
        title = viewState.selectedDateString.getString(),
        onTitleClicked = {
            dialogState.show()
        }
    )
}

@Composable
private fun TaskListEmptyState() {
    Box(
        modifier =
        Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = stringResource(R.string.no_tasks_scheduled_label),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier =
            Modifier
                .padding(32.dp)
                .align(Alignment.Center)
                .adaptiveWidth(),
        )
    }
}

@Suppress("MagicNumber")
@Composable
private fun TaskListToolbar(
    onLeftButtonClicked: () -> Unit,
    onRightButtonClicked: () -> Unit,
    title: String,
    onTitleClicked: () -> Unit = {},
) {
    val toolbarHeight = 84.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
            Modifier
                .statusBarsPadding()
                .height(toolbarHeight)
                .adaptiveWidth(),
        ) {
            ToolbarIconButton(
                icon = Icons.Default.KeyboardArrowLeft,
                onClick = { onLeftButtonClicked.invoke() },
                contentDescription = stringResource(R.string.view_previous_day_content_description),
                toolbarHeight = toolbarHeight,
            )

            val durationMillisForCrossFadeTitle = 500

            Crossfade(
                targetState = title,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp)
                    .clickable {
                        onTitleClicked.invoke()
                    }
                    .height(toolbarHeight),
                animationSpec = tween(durationMillisForCrossFadeTitle),
            ) { title ->
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            ToolbarIconButton(
                icon = Icons.Default.KeyboardArrowRight,
                onClick = { onRightButtonClicked.invoke() },
                contentDescription = stringResource(R.string.view_next_day_content_description),
                toolbarHeight = toolbarHeight,
            )
        }
    }
}

@Composable
private fun ToolbarIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String,
    toolbarHeight: Dp,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.testTag(NEXT_DAY_BUTTON_TAG)
    ) {
        Icon(
            icon,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(toolbarHeight)
        )
    }
}

@Composable
private fun AddTaskButton(onAddButtonClicked: () -> Unit) {
    FloatingActionButton(
        onClick = onAddButtonClicked,
        shape = CircleShape,
        modifier = Modifier
            .navigationBarsPadding()
            .testTag(ADD_TASK_BUTTON_TAG)
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = stringResource(R.string.add_task),
        )
    }
}

@Suppress("MagicNumber")
class TaskListViewStateProvider : PreviewParameterProvider<TaskListViewState> {
    override val values: Sequence<TaskListViewState>
        get() {
            val incompleteTasks =
                (1..3).map { index ->
                    Task(
                        id = "$index",
                        description = "Test task: $index",
                        scheduledDateMillis =
                        LocalDate.now().toEpochMillis(),
                        completed = false,
                    )
                }

            val completedTasks =
                (1..3).map { index ->
                    Task(
                        id = "$index",
                        description = "Test task: $index",
                        scheduledDateMillis =
                        LocalDate.now().toEpochMillis(),
                        completed = true,
                    )
                }

            val loadingState =
                TaskListViewState(
                    showLoading = true,
                )

            val taskListState =
                TaskListViewState(
                    showLoading = false,
                    incompleteTasks = incompleteTasks,
                    completedTasks = completedTasks,
                )

            val emptyState =
                TaskListViewState(
                    showLoading = false,
                    incompleteTasks = emptyList(),
                    completedTasks = emptyList(),
                )

            val errorState =
                TaskListViewState(
                    showLoading = false,
                    errorMessage = UIText.StringText("Something went wrong"),
                )

            return sequenceOf(loadingState, taskListState, emptyState, errorState)
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
@Preview(
    name = "Medium",
    widthDp = 700,
)
@Preview(
    name = "Expanded",
    widthDp = 900,
)
@ExcludeFromJacocoGeneratedReport
@Composable
private fun TaskListContentPreview(
    @PreviewParameter(TaskListViewStateProvider::class)
    viewState: TaskListViewState,
) {
    OPBTheme {
        TaskListContent(
            viewState = viewState,
            onRescheduleClicked = {},
            onDoneClicked = {},
            onAddButtonClicked = {},
            onPreviousDateButtonClicked = {},
            onNextDateButtonClicked = {},
            onDateSelected = {},
            onTaskRescheduled = { _, _ ->
            }
        )
    }
}
