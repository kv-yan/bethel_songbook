package ru.betel.app.ui.transformation

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class ArrowVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Handle spaces correctly and avoid empty transformations
        val parts = text.text.split(" ").filter { it.isNotBlank() }
        val transformedText = parts.joinToString(" -> ") { part ->
            if (part.startsWith("-") || part.startsWith("+")) "'$part'" else part
        }

        val cursorOffsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return transformedText.length.coerceAtMost(offset)
            }

            override fun transformedToOriginal(offset: Int): Int {
                return text.text.length.coerceAtMost(offset)
            }
        }

        return TransformedText(
            AnnotatedString(transformedText), cursorOffsetMapping
        )
    }
}
