package com.example.androidpractice.core.designsystem.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.androidpractice.core.designsystem.R
import com.example.androidpractice.core.designsystem.theme.AppTheme

private val TopAppBarHeight = 56.dp

@Composable
fun CenteredTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp),
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTextStyle provides MaterialTheme.typography.h6
    ) {
        Box(
            modifier = Modifier
                .height(
                    TopAppBarHeight
                )
                .background(backgroundColor)
                .then(modifier)
        ) {
            val composables = listOf(navigationIcon, title, actions)
            Layout(
                contents = composables,
            ) { measurables, constraints ->
                val placeables = measurables.map { measurables ->
                    measurables.map {
                        it.measure(constraints)
                    }
                }
                layout(constraints.maxWidth, constraints.maxHeight) {
                    val navIconPlaceables = placeables[0]
                    var navIconXPosition =
                        contentPadding.calculateStartPadding(LayoutDirection.Ltr).toPx().toInt()

                    val titlePlaceables = placeables[1]
                    var titleXPosition =
                        (constraints.maxWidth - titlePlaceables.sumOf { it.width }) / 2

                    val actionsPlaceables = placeables[2]
                    var actionsXPosition =
                        constraints.maxWidth - contentPadding.calculateStartPadding(LayoutDirection.Ltr)
                            .toPx().toInt() - actionsPlaceables.sumOf { it.width }
                    navIconPlaceables.forEach { placeable ->
                        val yPosition = (constraints.maxHeight - placeable.height) / 2
                        placeable.placeRelative(navIconXPosition, yPosition)
                        navIconXPosition += placeable.width
                    }
                    titlePlaceables.forEach { placeable ->
                        val yPosition = (constraints.maxHeight - placeable.height) / 2
                        placeable.placeWithLayer(titleXPosition, yPosition)
                        titleXPosition += placeable.width
                    }
                    actionsPlaceables.forEach { placeable ->
                        val yPosition = (constraints.maxHeight - placeable.height) / 2
                        placeable.placeWithLayer(actionsXPosition, yPosition)
                        actionsXPosition += placeable.width
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTopAppBar() {
    AppTheme {
        CenteredTopAppBar(
            title = { Text("Авторизация") },
            navigationIcon = {
                IconButton(onClick = {
                    Log.d("TAG", "PreviewTopAppBar: ")
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        painterResource(id = R.drawable.ic_camera), null
                    )
                }
            }
        )
    }
}