package ru.betel.app.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.fieldBg
import ru.betel.app.ui.theme.textFieldPlaceholder
import ru.betel.app.ui.transformation.ArrowVisualTransformation


@Composable
fun ModulationTextFields(
    placeholder: String,
    modifier: Modifier = Modifier,
    fieldText: MutableState<String>,
    imeAction: ImeAction = ImeAction.Default,
    fontSize: TextUnit = 14.sp,
    singleLine: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    align: Alignment.Vertical = Alignment.CenterVertically
) {
    Surface(
        shape = shape, color = fieldBg
    ) {
        Column(
            Modifier.padding(vertical = 9.dp)
        ) {
            BasicTextField(
                value = fieldText.value,
                onValueChange = { newText ->
                    // Update the state with raw input, avoiding any crashes
                    fieldText.value = newText
                },
                singleLine = singleLine,
                modifier = modifier
                    .height(20.dp)
                    .padding(start = 10.dp),
                textStyle = TextStyle(
                    fontSize = fontSize,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Start,
                    color = Color.Black
                ),
                decorationBox = { innerTextField ->
                    Row(modifier, verticalAlignment = align) {
                        Box(Modifier.weight(1f)) {
                            if (fieldText.value.isEmpty()) {
                                Text(
                                    placeholder,
                                    style = LocalTextStyle.current.copy(
                                        fontSize = fontSize, color = textFieldPlaceholder
                                    ),
                                )
                            }
                            innerTextField()
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = imeAction
                ),
                visualTransformation = ArrowVisualTransformation()
            )
        }
    }
}


fun transformInput(input: String): String {
    // Split by spaces, filter out blanks, and join with the arrow notation
    return input.split(" ").filter { it.isNotBlank() }.joinToString(" -> ") { part ->
        if (part.startsWith("-") || part.startsWith("+")) "'$part'" else part
    }
}
