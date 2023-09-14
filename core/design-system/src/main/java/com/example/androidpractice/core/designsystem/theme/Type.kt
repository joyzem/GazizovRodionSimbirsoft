package com.example.androidpractice.core.designsystem.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.androidpractice.core.designsystem.R

val Type = Typography(
    h6 = TextStyle(
        fontFamily = FontFamily(Font(R.font.officina_sans_scc_extrabold, FontWeight.Normal)),
        fontSize = 21.sp
    )
)

val TextStylePopUpPlaceholder
    @Composable get() = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = black38
    )

val TextStylePopupButton
    @Composable get() = TextStyle(
        fontSize = 14.sp,
        color = leaf,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium
    )

val OfficinaSansExtraBoldSCC @Composable get() = TextStyle(
    fontFamily = FontFamily(Font(R.font.officina_sans_scc_extrabold)),
    fontSize = 21.sp,
    lineHeight = 23.sp,
    color = blueGrey
)

val TextStyle10 @Composable get() = TextStyle(
    fontSize = 14.sp,
    color = black70
)

val TextStyle6 @Composable get() = TextStyle(
    fontSize = 14.sp,
    color = whiteColor,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Medium
)