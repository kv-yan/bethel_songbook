package ru.betel.app.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.fieldBg
import ru.betel.app.ui.theme.textFieldPlaceholder
import ru.betel.domain.model.ui.AppTheme


@Composable
fun MyTextFields(
    appTheme: AppTheme,
    placeholder: String,
    modifier: Modifier = Modifier,
    fieldText: MutableState<String>,
    imeAction: ImeAction = ImeAction.Default,
    textType: KeyboardType = KeyboardType.Text,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    fontSize: TextUnit = 14.sp,
    singleLine: Boolean = false,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    align: Alignment.Vertical = Alignment.CenterVertically,
    onDataSave: (String) -> Unit = {}
) {

    var text by rememberSaveable { mutableStateOf(fieldText.value) }

    Surface(
        shape = shape, color = appTheme.fieldBackgroundColor
    ) {
        Column(
            Modifier.padding(vertical = 9.dp)
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    fieldText.value = it
                    onDataSave.invoke(it)
                },
                singleLine = singleLine,
                modifier = if (singleLine && fontSize < 14.sp) {
                    modifier
                        .height(20.dp)
                        .padding(start = 10.dp)
                } else {
                    modifier.padding(start = 10.dp)
                },
                textStyle = TextStyle(
                    fontSize = fontSize,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(500),
                    textAlign = if (singleLine) TextAlign.Center else TextAlign.Start,
                    color = appTheme.primaryTextColor,
                ),
                cursorBrush = SolidColor(appTheme.primaryTextColor),
                decorationBox = { innerTextField ->
                    Row(modifier, verticalAlignment = align) {
                        if (leadingIcon != null) {
                            leadingIcon()
                            Spacer(Modifier.width(8.dp))
                        }

                        Box(Modifier.weight(1f)) {
                            if (text.isEmpty()) {
                                Text(
                                    placeholder,
                                    style = LocalTextStyle.current.copy(
                                        fontSize = fontSize, color = appTheme.primaryTextColor
                                    ),
                                )
                            }
                            innerTextField()
                        }
                        if (trailingIcon != null) trailingIcon()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = imeAction
                ),
                visualTransformation = if (textType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None
            )
        }
    }
}

@Composable
fun MyTextFieldsForEditScreen(
    placeholder: String,
    modifier: Modifier = Modifier,
    fieldText: MutableState<String>,
    imeAction: ImeAction = ImeAction.Default,
    textType: KeyboardType = KeyboardType.Text,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    fontSize: TextUnit = 14.sp,
    singleLine: Boolean = false,
) {

    var text by rememberSaveable { mutableStateOf(fieldText.value) }

    Surface(
        shape = RoundedCornerShape(8.dp), color = fieldBg
    ) {

        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                fieldText.value = it
            },
            singleLine = singleLine,
            modifier = modifier
                .heightIn(min = 38.dp)
                .padding(start = 10.dp),
            textStyle = TextStyle(
                fontSize = fontSize,
                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                fontWeight = FontWeight(400),
                textAlign = if (singleLine) TextAlign.Center else TextAlign.Start,
                color = Color.Black,
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier,
                ) {
                    if (leadingIcon != null) leadingIcon()
                    Box(Modifier.weight(1f)) {
                        if (text.isEmpty()) Text(
                            placeholder,
                            style = LocalTextStyle.current.copy(
                                fontSize = fontSize, color = textFieldPlaceholder
                            ),
                        )
                        innerTextField()
                    }
                    if (trailingIcon != null) trailingIcon()
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction
            ),
            visualTransformation = if (textType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}

@Composable
fun MyTextFields(
    placeholder: String,
    modifier: Modifier = Modifier,
    fieldText: MutableState<String>,
    imeAction: ImeAction = ImeAction.Default,
    textType: KeyboardType = KeyboardType.Text,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
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
                onValueChange = {
                    fieldText.value = it
                    fieldText.value = it
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
                        if (leadingIcon != null) leadingIcon()

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
                        if (trailingIcon != null) trailingIcon()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = imeAction
                ),
                visualTransformation = if (textType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None
            )

        }
    }
}

