package com.example.androidpractice.core.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidpractice.core.designsystem.R
import com.example.androidpractice.core.designsystem.theme.AppTheme
import com.example.androidpractice.core.designsystem.theme.TextStylePopUpPlaceholder
import com.example.androidpractice.core.designsystem.theme.black38
import com.example.androidpractice.core.designsystem.theme.warmGreyTwo

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    labelTextStyle: TextStyle = TextStyle.Default.copy(color = black38, fontSize = 12.sp),
    hint: String = "",
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    textStyle: TextStyle = TextStylePopUpPlaceholder.copy(Color.Black)
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = { innerTextField ->
            Column {
                Text(text = label, style = labelTextStyle)
                Spacer(modifier = Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.weight(1f).padding(end = 8.dp)){
                        innerTextField()
                        if (value.isEmpty()) {
                            Text(hint, style = TextStylePopUpPlaceholder)
                        }
                    }
                    CompositionLocalProvider(LocalContentColor provides warmGreyTwo) {
                        Box(modifier = Modifier.size(24.dp)) { trailingIcon?.let { it() } }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewTextField() {
    AppTheme {
        var input by remember { mutableStateOf("test vetyfsfdkjfslkdjvetyfsfdkjfslkdjvetyfsfdkjfslkdjvetyfsfdkjfslkdj ") }
        AppTextField(
            value = input,
            onValueChange = { input = it },
            hint = "Test",
            label = "email",
            modifier = Modifier.padding(16.dp),
            trailingIcon = {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(24.dp)) {
                    Icon(painterResource(id = R.drawable.ic_eye_close), null)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTextFieldEmpty() {
    AppTheme {
        var input by remember { mutableStateOf("") }
        AppTextField(
            value = input,
            onValueChange = { input = it },
            hint = "Test",
            label = "email",
            modifier = Modifier.padding(16.dp),
            trailingIcon = {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(24.dp)) {
                    Icon(painterResource(id = R.drawable.ic_eye_close), null)
                }
            }
        )
    }
}