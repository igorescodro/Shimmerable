package com.escodro.shimmer.ui.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.escodro.shimmer.ui.theme.ExampleTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun Modifier.shimmerable(
    shape: Shape = RoundedCornerShape(8.dp),
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
): Modifier {
    if (!LocalShimmerState.current.isLoading) return this

    return this
        .shimmer()
        .background(color = color, shape = shape)
        .drawWithContent {
            // Do not draw the actual content.
        }
}

/**
 * Provider for the shimmer effect.
 */
@Composable
fun ShimmerProvider(isLoading: Boolean = true, content: @Composable (Boolean) -> Unit) {
    CompositionLocalProvider(
        value = LocalShimmerState provides ShimmerState(isLoading = isLoading),
        content = { content(isLoading) },
    )
}

/**
 * State of the shimmer effect.
 */
data class ShimmerState(val isLoading: Boolean)

/**
 * Local composition local for the shimmer effect.
 */
val LocalShimmerState = compositionLocalOf { ShimmerState(isLoading = false) }

@PreviewLightDark
@Composable
private fun ShimmerablePreview() {
    ExampleTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
        ) {
            Text(
                text = "Keep this to yourself!",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.shimmerable()
            )
            ShimmerProvider {
                Text(
                    text = "Keep this to yourself!",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.shimmerable()
                )
            }
        }
    }
}
