package com.onepercentbetter.core.ui.components

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import com.onepercentbetter.ExcludeFromJacocoGeneratedReport
import com.onepercentbetter.core.ui.theme.OPBTheme

/**
 * This is our custom version of a [TextButton] that ensures that supplied [text] is uppercase
 */
@ExcludeFromJacocoGeneratedReport
@Composable
fun OPBTextButton(
    text: String,
    onClick: () -> Unit,
) {
    TextButton(onClick = { onClick.invoke() }) {
        Text(
            text = text.toUpperCase(Locale.current),
            style = MaterialTheme.typography.labelSmall,
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
@ExcludeFromJacocoGeneratedReport
@Composable
private fun OPBTextButtonPreview() {
    OPBTheme {
        OPBTextButton(
            text = "OPB Text button",
            onClick = {},
        )
    }
}
