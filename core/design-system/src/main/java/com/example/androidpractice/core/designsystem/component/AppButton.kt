package com.example.androidpractice.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidpractice.core.designsystem.theme.AppTheme

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(onClick = onClick, modifier = modifier.heightIn(44.dp), enabled = enabled) {
        content()
    }
}

@Preview
@Composable
private fun PreviewAppButton() {
    AppTheme {
        AppButton(onClick = { }) {
            Text("Войти")
        }
    }
}
