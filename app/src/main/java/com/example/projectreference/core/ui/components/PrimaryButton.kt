package com.example.projectreference.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import com.example.projectreference.R
import com.example.projectreference.core.ui.theme.ButtonShape
import com.example.projectreference.core.ui.theme.TOATheme

/**
 * This is custom [Button] that provides shape and styling expected
 * in the TOA app.
 *
 * @param: [text] The text inside the button
 * @param: [onClick] A callback invoked when the user clicks the button
 * @param: [modifier] An optional [Modifier] to configure this component
 * @param: [backgroundColor] The color of the button in an enabled state
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    isEnabled: Boolean = true
) {
    val buttonColors = buttonColors(
        backgroundColor = backgroundColor,
    )

    Button(
        onClick = onClick,
        colors = buttonColors,
        shape = ButtonShape,
        modifier = modifier
            .height(dimensionResource(id = R.dimen.button_height))
            .fillMaxWidth(),
        enabled = isEnabled
    ) {
        Text(
            text = text.toUpperCase(Locale.current),
        )
    }
}

@Preview(
    name = "Night mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PrimaryEnabledButtonPreview() {
    TOATheme {
        PrimaryButton(
            text = "Primary Button",
            onClick = {},
            isEnabled = true
        )
    }
}

@Preview(
    name = "Night mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun PrimaryDisabledButtonPreview() {
    TOATheme {
        PrimaryButton(
            text = "Primary Button",
            onClick = {},
            isEnabled = false
        )
    }
}