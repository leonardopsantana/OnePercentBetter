package com.onepercentbetter.addtask.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.onepercentbetter.ExcludeFromJacocoGeneratedReport
import com.onepercentbetter.R
import com.onepercentbetter.addtask.domain.model.TaskInput
import com.onepercentbetter.core.ui.components.OPBDatePickerDialog
import com.onepercentbetter.core.ui.components.OPBDatePickerInput
import com.onepercentbetter.core.ui.components.OPBTextField
import com.onepercentbetter.core.ui.components.PrimaryButton
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.core.ui.components.VerticalSpacer
import com.onepercentbetter.core.ui.components.getString
import com.onepercentbetter.core.ui.theme.OPBTheme
import java.time.LocalDate

const val ADD_TASK_DESCRIPTION_INPUT_TAG = "ADD_TASK_DESCRIPTION_INPUT"

@Suppress("FunctionNaming")
@Composable
fun AddTaskContent(
    viewState: AddTaskViewState,
    onTaskDescriptionChanged: (String) -> Unit,
    onTaskScheduleDateChanged: (LocalDate) -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val descriptionFocusRequester = remember { FocusRequester() }

    LaunchedEffect(true) {// calls only one time
        descriptionFocusRequester.requestFocus()
    }

    Box(
        modifier = modifier,
    ) {
        AddTaskInputsColumn(
            viewState = viewState,
            onTaskDescriptionChanged = onTaskDescriptionChanged,
            onTaskScheduleDateChanged = onTaskScheduleDateChanged,
            onSubmitClicked = onSubmitClicked,
            modifier = Modifier.fillMaxWidth(),
            descriptionFocusRequester = descriptionFocusRequester,
        )

        if (viewState is AddTaskViewState.Submitting) {
            CircularProgressIndicator(
                modifier =
                Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
            )
        }
    }
}

@Composable
private fun AddTaskInputsColumn(
    viewState: AddTaskViewState,
    onTaskDescriptionChanged: (String) -> Unit,
    onTaskScheduleDateChanged: (LocalDate) -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier,
    descriptionFocusRequester: FocusRequester,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.form_spacing)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TaskDescriptionLabel()
        TaskDescriptionInput(
            text = viewState.taskInput.description,
            onTextChanged = onTaskDescriptionChanged,
            enabled = viewState.inputsEnabled,
            errorMessage =
                (viewState as? AddTaskViewState.Active)
                    ?.descriptionInputErrorMessage
                    ?.getString(),
            descriptionFocusRequester = descriptionFocusRequester
        )
        TaskDateLabel()
        TaskDateInput(
            value = viewState.taskInput.scheduledDate,
            onValueChanged = onTaskScheduleDateChanged,
        )
        if (viewState is AddTaskViewState.SubmissionError) {
            Text(
                text = viewState.errorMessage.getString(),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 12.dp),
            )
        }
        VerticalSpacer(height = dimensionResource(id = R.dimen.form_spacing))
        SubmitButton(
            onSubmitClicked = onSubmitClicked,
            enabled = viewState.inputsEnabled,
        )
    }
}

@Composable
private fun SubmitButton(
    onSubmitClicked: () -> Unit,
    enabled: Boolean,
) {
    PrimaryButton(
        text = stringResource(R.string.submit),
        onClick = { onSubmitClicked.invoke() },
        enabled = enabled,
    )
}

@Composable
private fun TaskDateInput(
    value: LocalDate,
    onValueChanged: (LocalDate) -> Unit
) {
    OPBDatePickerInput(
        value = value,
        onValueChanged = onValueChanged,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun TaskDateLabel() {
    Text(
        text = "When would you like to do it?",
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun TaskDescriptionInput(
    text: String,
    onTextChanged: (String) -> Unit,
    enabled: Boolean,
    errorMessage: String?,
    descriptionFocusRequester: FocusRequester,
) {
    OPBTextField(
        text = text,
        onTextChanged = onTextChanged,
        labelText = null,
        enabled = enabled,
        keyboardOptions =
            KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
            ),
        placeholderText = stringResource(R.string.task_input_placeholder),
        errorMessage = errorMessage,
        focusRequester = descriptionFocusRequester,
        modifier = Modifier.testTag(ADD_TASK_DESCRIPTION_INPUT_TAG)
    )
}

@Composable
private fun TaskDescriptionLabel() {
    Text(
        text = "What would you like to accomplish?",
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
    )
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
    widthDp = 840,
)
@ExcludeFromJacocoGeneratedReport
@Composable
private fun AddTaskContentPreview(
    @PreviewParameter(AddTaskViewStateProvider::class)
    addTaskViewState: AddTaskViewState,
) {
    OPBTheme {
        AddTaskContent(
            viewState = addTaskViewState,
            onTaskDescriptionChanged = {},
            onTaskScheduleDateChanged = {},
            onSubmitClicked = {},
            modifier =
            Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.screen_padding)),
        )
    }
}

class AddTaskViewStateProvider : PreviewParameterProvider<AddTaskViewState> {
    override val values: Sequence<AddTaskViewState>
        get() {
            val activeInput =
                TaskInput(
                    description = "Clean the office",
                    scheduledDate = LocalDate.now(),
                )

            return sequenceOf(
                AddTaskViewState.Initial(),
                AddTaskViewState.Active(
                    taskInput = activeInput,
                ),
                AddTaskViewState.Submitting(
                    taskInput = activeInput,
                ),
                AddTaskViewState.SubmissionError(
                    taskInput = activeInput,
                    errorMessage = UIText.StringText("Whoops"),
                ),
                AddTaskViewState.Completed,
            )
        }
}
