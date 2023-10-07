package com.example.androidpractice.feature.news

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.androidpractice.core.designsystem.theme.AppTheme
import com.example.androidpractice.core.model.event.Event
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.Rule
import org.junit.Test

class NewsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun event_items_should_display_title_text() {
        composeTestRule.setContent {
            AppTheme {
                NewsScreen(news = events, onEventClick = {}, onFiltersClick = {})
            }
        }
        events.forEach {
            composeTestRule.onNodeWithTag("title_${it.id}", useUnmergedTree = true).assertTextEquals(it.title)
        }
    }

    companion object {
        private val events = listOf(
            Event(
                id = "1",
                title = "Спонсоры отремонтируют школу-интернат",
                subtitle = "Участники и болельщики смогли весело и активно провести время на «Петербургском благотворительном марафоне» и при этом финансово поучаствовать в помощи детям.\\nПри этом финансово поучаствовать в помощи детям. При этом финансово поучаствовать в помощи детям.",
                description = "Участники и болельщики смогли весело и активно провести время на «Петербургском благотворительном марафоне» и при этом финансово поучаствовать в помощи детям.\\nПри этом финансово поучаствовать в помощи детям. При этом финансово поучаствовать в помощи детям.",
                sponsor = "Благотворительный Фонд «Счастливый Мир»",
                startDate = Instant.fromEpochMilliseconds(1692662400000)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date,
                endDate = Instant.fromEpochMilliseconds(1693008000000)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date,
                createAt = Instant.fromEpochMilliseconds(1692489600000)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date,
                address = "Санкт-Петербург, Кирочная улица,\\nд. 50А, каб. 208",
                phoneNumber = "+7 (937) 037 37-73",
                email = "example@gmail.com",
                siteUrl = "google.com",
                imagePreview = "https://wanthelp-112222ed0ca0.herokuapp.com/events/img.png",
                imageUrls = listOf(
                    "https://wanthelp-112222ed0ca0.herokuapp.com/events/img.png",
                    "https://wanthelp-112222ed0ca0.herokuapp.com/events/img_1.png",
                    "https://wanthelp-112222ed0ca0.herokuapp.com/events/img_2.png"
                ),
                category = "1"
            ),
            Event(
                id = "2",
                title = "Спонсоры отремонтируют школу-интернат",
                subtitle = "Участники и болельщики смогли весело и активно провести время на «Петербургском благотворительном марафоне» и при этом финансово поучаствовать в помощи детям.\\nПри этом финансово поучаствовать в помощи детям. При этом финансово поучаствовать в помощи детям.",
                description = "Участники и болельщики смогли весело и активно провести время на «Петербургском благотворительном марафоне» и при этом финансово поучаствовать в помощи детям.\\nПри этом финансово поучаствовать в помощи детям. При этом финансово поучаствовать в помощи детям.",
                sponsor = "Благотворительный Фонд «Счастливый Мир»",
                startDate = Instant.fromEpochMilliseconds(1692662400000)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date,
                endDate = Instant.fromEpochMilliseconds(1693008000000)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date,
                createAt = Instant.fromEpochMilliseconds(1692489600000)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date,
                address = "Санкт-Петербург, Кирочная улица,\\nд. 50А, каб. 208",
                phoneNumber = "+7 (937) 037 37-73",
                email = "example@gmail.com",
                siteUrl = "google.com",
                imagePreview = "https://wanthelp-112222ed0ca0.herokuapp.com/events/img.png",
                imageUrls = listOf(
                    "https://wanthelp-112222ed0ca0.herokuapp.com/events/img.png",
                    "https://wanthelp-112222ed0ca0.herokuapp.com/events/img_1.png",
                    "https://wanthelp-112222ed0ca0.herokuapp.com/events/img_2.png"
                ),
                category = "2"
            )
        )
    }
}