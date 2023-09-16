package com.example.androidpractice.feature.news_details_impl.money.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidpractice.core.designsystem.theme.AppTheme
import com.example.androidpractice.core.designsystem.theme.TextStyle6
import com.example.androidpractice.core.designsystem.theme.TextStylePopupButton
import com.example.androidpractice.core.designsystem.theme.leaf
import com.example.androidpractice.core.designsystem.theme.whiteColor
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@Composable
internal fun DonationOptionPicker(
    selectedOption: Int,
    possibleOptions: List<Int>,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .border(BorderStroke(1.dp, leaf), RoundedCornerShape(2.dp))
            .clip(RoundedCornerShape(2.dp))
    ) {
        possibleOptions.forEach { money ->
            DonationOption(
                option = money,
                selected = selectedOption == money,
                onOptionSelected = onOptionSelected
            )
        }
    }
}

@Composable
private fun DonationOption(
    option: Int,
    selected: Boolean,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .background(if (selected) leaf else whiteColor)
            .width(68.dp)
            .clickable { onOptionSelected(option) },
        contentAlignment = Alignment.Center
    ) {
        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("ru", "RU")).apply {
            currency = Currency.getInstance("RUB")
            maximumFractionDigits = 0
        }
        Text(
            text = currencyFormatter.format(option),
            style = if (selected) TextStyle6 else TextStylePopupButton,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Preview
@Composable
fun MoneyPickerPreview() {
    AppTheme {
        DonationOptionPicker(
            500,
            listOf(100, 500, 1000, 2000),
            {}
        )
    }
}
