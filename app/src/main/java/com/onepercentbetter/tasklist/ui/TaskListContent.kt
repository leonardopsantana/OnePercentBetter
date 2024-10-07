package com.onepercentbetter.tasklist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.onepercentbetter.ExcludeFromJacocoGeneratedReport
import com.onepercentbetter.R
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.core.ui.AlertMessage
import com.onepercentbetter.core.ui.adaptiveWidth
import com.onepercentbetter.core.ui.components.Material3CircularProgressIndicator
import com.onepercentbetter.core.ui.components.OPBDatePickerDialog
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.core.ui.components.getString
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.onepercentbetter.toEpochMillis
import com.onepercentbetter.toEpochMillisUTC
import com.onepercentbetter.toLocalDateUTC
import kotlinx.coroutines.launch
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
    onDateSelected: (LocalDate) -> Unit,
    onTaskRescheduled: (Task, LocalDate) -> Unit,
    onReschedulingCompleted: () -> Unit,
    onAlertMessageShown: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    TaskListSnackbar(
        alertMessage = viewState.alertMessages.firstOrNull(),
        snackbarHostState = snackbarHostState,
        onAlertMessageShown = onAlertMessageShown,
    )

    Scaffold(
        floatingActionButton = {
            AddTaskButton(onAddButtonClicked)
        },
        topBar = {
            ToolBarWithDialog(
                viewState,
                onDateSelected
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = modifier,
    ) { paddingValues ->
        if (viewState.showTasks) {
            if (viewState.incompleteTasks.isNullOrEmpty() &&
                viewState.completedTasks.isNullOrEmpty()
            ) {
                TaskListEmptyState()
            } else {
                RescheduleTaskDialog(
                    viewState = viewState,
                    onTaskRescheduled = onTaskRescheduled,
                    onDismissed = onReschedulingCompleted
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
            TaskListLoadingContent()
        }
    }
}

@Composable
private fun TaskListLoadingContent() {
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

@Composable
private fun TaskListSnackbar(
    alertMessage: AlertMessage?,
    snackbarHostState: SnackbarHostState,
    onAlertMessageShown: (Long) -> Unit,
) {
    if (alertMessage != null) {
        val message = alertMessage.message.getString()
        val actionLabel = alertMessage.actionText?.getString()
        val duration = when (alertMessage.duration) {
            AlertMessage.Duration.SHORT -> SnackbarDuration.Short
            AlertMessage.Duration.LONG -> SnackbarDuration.Long
            AlertMessage.Duration.INDEFINITE -> SnackbarDuration.Indefinite
        }

        LaunchedEffect(alertMessage.id) {
            val snackbarResult = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = duration,
            )

            onAlertMessageShown.invoke(alertMessage.id)

            when (snackbarResult) {
                SnackbarResult.Dismissed -> {
                    alertMessage.onDismissed.invoke()
                }

                SnackbarResult.ActionPerformed -> {
                    alertMessage.onActionClicked.invoke()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RescheduleTaskDialog(
    viewState: TaskListViewState,
    onTaskRescheduled: (Task, LocalDate) -> Unit,
    onDismissed: () -> Unit
) {
    val taskToReschedule = viewState.taskToReschedule

    if (taskToReschedule != null) {
        OPBDatePickerDialog(
            datePickerState = rememberDatePickerState(
                initialSelectedDateMillis =
                taskToReschedule.scheduledDateMillis
                    .toLocalDateUTC()
                    .toEpochMillisUTC(),
            ),
            onDismiss = onDismissed,
            onComplete = { selectedDateMillis ->
                if (selectedDateMillis != null) {
                    val newDate = selectedDateMillis.toLocalDateUTC()
                    onTaskRescheduled.invoke(taskToReschedule, newDate)
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolBarWithDialog(
    viewState: TaskListViewState,
    onDateSelected: (LocalDate) -> Unit
) {
    val showDatePickerDialog = remember { mutableStateOf(false) }

    if (showDatePickerDialog.value) {
        OPBDatePickerDialog(
            datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = viewState.selectedDate.toEpochMillisUTC(),
            ),
            onDismiss = {
                showDatePickerDialog.value = false
            },
            onComplete = { selectedDateMillis ->
                showDatePickerDialog.value = false

                if (selectedDateMillis != null) {
                    onDateSelected.invoke(selectedDateMillis.toLocalDateUTC())
                }
            }
        )
    }

    TaskListToolbar(
        title = viewState.selectedDateString.getString(),
        onCalendarIconClicked = {
            showDatePickerDialog.value = true
        },
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

@Composable
internal fun ToolbarIconButton(
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
            onDateSelected = {},
            onTaskRescheduled = { _, _ ->
            },
            onReschedulingCompleted = {},
            onAlertMessageShown = {}
        )
    }
}
