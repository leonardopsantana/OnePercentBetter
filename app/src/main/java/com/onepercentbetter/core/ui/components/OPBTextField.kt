package com.onepercentbetter.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onepercentbetter.ExcludeFromJacocoGeneratedReport
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.onepercentbetter.core.ui.theme.TextFieldShape

/**
 * This is a custom implementation of an [OutlinedTextField] to ensure that is has the OnePercentBetter branding and
 * styling as expected.
 *
 * @param[text] The current text inside the input.
 * @param[onTextChanged] A callback invoked whenever the user modifies the text inside this input.
 * @param[labelText] The label that shows above the input when focused
 * @param[modifier] An optional [Modifier] to configure this component
 * @param[focusRequester] An optional [FocusRequester] to request focus on this input
 */
@ExcludeFromJacocoGeneratedReport
@Composable
fun OPBTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    labelText: String?,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onFocusChanged: (FocusState) -> Unit = {},
    placeholderText: String? = null,
    focusRequester: FocusRequester = FocusRequester()
) {
    val labelComposable: (@Composable () -> Unit)? =
        labelText?.let {
            @Composable {
                Text(
                    text = labelText,
                )
            }
        }

    val placeholderComposable: (@Composable () -> Unit)? =
        placeholderText?.let {
            @Composable {
                Text(
                    text = placeholderText,
                )
            }
        }

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChanged,
            label = labelComposable,
            shape = TextFieldShape,
            modifier =
                modifier
                    .heightIn(dimensionResource(id = R.dimen.text_field_height))
                    .fillMaxWidth()
                    .onFocusChanged(onFocusChanged)
                    .focusRequester(focusRequester),
            isError = (errorMessage != null),
            visualTransformation = visualTransformation,
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            placeholder = placeholderComposable,
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier =
                    Modifier
                        .padding(
                            top = 4.dp,
                            start = 16.dp,
                        ),
            )
        }
    }
}

@Preview(
    name = "Night mode - Filled",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode - Filled",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@ExcludeFromJacocoGeneratedReport
@Composable
private fun FilledOPBTextFieldPreview() {
    OPBTheme {
        Surface {
            OPBTextField(
                text = "OnePercentBetter Text Field",
                onTextChanged = {},
                labelText = "Label",
            )
        }
    }
}

@Preview(
    name = "Night mode - Error",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode - Error",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@ExcludeFromJacocoGeneratedReport
@Composable
private fun ErrorOPBTextFieldPreview() {
    OPBTheme {
        Surface {
            OPBTextField(
                text = "OnePercentBetter Text Field",
                onTextChanged = {},
                labelText = "Label",
                errorMessage = "Please, enter this",
            )
        }
    }
}

@Preview(
    name = "Night mode - Empty",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode - Empty",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@ExcludeFromJacocoGeneratedReport
@Composable
private fun EmptyOPBTextFieldPreview() {
    OPBTheme {
        Surface {
            OPBTextField(
                text = "",
                onTextChanged = {},
                labelText = "Label",
            )
        }
    }
}
