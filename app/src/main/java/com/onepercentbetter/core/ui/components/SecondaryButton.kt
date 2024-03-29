package com.onepercentbetter.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.textButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import com.onepercentbetter.ExcludeFromJacocoGeneratedReport
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.theme.ButtonShape
import com.onepercentbetter.core.ui.theme.OPBTheme

/**
 * This is custom [Button] that provides shape and styling expected
 * in the OnePercentBetter app.
 *
 * @param: [text] The text inside the button
 * @param: [onClick] A callback invoked when the user clicks the button
 * @param: [modifier] An optional [Modifier] to configure this component
 */
@ExcludeFromJacocoGeneratedReport
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.primary,
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
@ExcludeFromJacocoGeneratedReport
@Composable
private fun SecondaryEnabledButtonPreview() {
    OPBTheme {
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
@ExcludeFromJacocoGeneratedReport
@Composable
private fun SecondaryDisabledButtonPreview() {
    OPBTheme {
        Surface {
            SecondaryButton(
                text = "Secondary Button",
                onClick = {},
                isEnabled = false
            )
        }
    }
}
