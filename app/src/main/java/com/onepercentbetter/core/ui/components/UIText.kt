package com.onepercentbetter.core.ui.components

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * This is a sealed class that contains all of the different ways text can be presented to the UI.
 */
sealed class UIText {
    data class StringText(val value: String) : UIText()

    data class ResourceText(
        @StringRes val value: Int,
        val args: List<Any> = emptyList(),
    ) : UIText()
}

/**
 * Evaluates the value of this [UIText] based on its type.
 *
 * @param[context] If necessary, use this to evaluate a string resource.
 */
@Suppress("SpreadOperator")
fun UIText.getString(
    context: Context,
): String {
    return when (this) {
        is UIText.StringText -> this.value
        is UIText.ResourceText -> context.getString(this.value, *this.args.toTypedArray())
    }
}

/**
 * A helper function that allows us to get string from a [Composable] context.
 */
@Composable
fun UIText.getString(): String {
    return this.getString(LocalContext.current)
}
