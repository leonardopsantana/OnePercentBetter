package com.onepercentbetter.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.theme.ButtonShape
import com.onepercentbetter.core.ui.theme.OnePercentBetterTheme

/**
 * This is custom [Button] that provides shape and styling expected
 * in the OnePercentBetter app.
 *
 * @param: [text] The text inside the button
 * @param: [onClick] A callback invoked when the user clicks the button
 * @param: [modifier] An optional [Modifier] to configure this component
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colors.primary,
    isEnabled: Boolean = true
) {
    val buttonColors = textButtonColors(
        contentColor = contentColor,
    )
    TextButton(
        onClick = onClick,
        shape = ButtonShape,
        modifier = modifier
            .height(dimensionResource(id = R.dimen.button_height))
            .fillMaxWidth(),
        colors = buttonColors,
        enabled = isEnabled
    ) {
        Text(
            text = text.toUpperCase(Locale.current)
        )
    }
}

@Preview(
    name = "Night mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    group = "Enabled"
)
@Preview(
    name = "Day mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    group = "Enabled"
)
@Composable
private fun SecondaryEnabledButtonPreview() {
    OnePercentBetterTheme {
        Surface {
            SecondaryButton(
                text = "Secondary Button",
                onClick = {},
            )
        }
    }
}

@Preview(
    name = "Night mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    group = "Disabled"
)
@Preview(
    name = "Day mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    group = "Disabled"
)
@Composable
private fun SecondaryDisabledButtonPreview() {
    OnePercentBetterTheme {
        Surface {
            SecondaryButton(
                text = "Secondary Button",
                onClick = {},
                isEnabled = false
            )
        }
    }
}
