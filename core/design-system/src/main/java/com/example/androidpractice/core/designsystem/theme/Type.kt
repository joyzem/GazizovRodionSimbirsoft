package com.example.androidpractice.core.designsystem.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.unit.sp
import com.example.androidpractice.core.designsystem.R

val Type = Typography(
    h6 = TextStyle(
        fontFamily = FontFamily(Font(R.font.officina_sans_scc_extrabold, FontWeight.Normal)),
        fontSize = 21.sp,
    ),
)

val TextStylePopUpPlaceholder
    @Composable get() = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = black38
    )