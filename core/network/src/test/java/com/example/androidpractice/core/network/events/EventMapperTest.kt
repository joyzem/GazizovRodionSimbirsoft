package com.example.androidpractice.core.network.events

import com.example.androidpractice.core.model.event.Event
import com.example.utils.json.fromJson
import com.google.gson.Gson
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class EventMapperTest {

    @Test
    fun `map json to events`() {
        val events = Gson().fromJson<List<EventDTO>>(json).map { it.toModel() }
        val target = listOf(
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
        assertEquals(target, events)
    }

    companion object {
        private val json = """[{
              "id": "1",
              "name": "Спонсоры отремонтируют школу-интернат",
              "startDate": 1692662400000,
              "endDate": 1693008000000,
              "description": "Участники и болельщики смогли весело и активно провести время на «Петербургском благотворительном марафоне» и при этом финансово поучаствовать в помощи детям.\\nПри этом финансово поучаствовать в помощи детям. При этом финансово поучаствовать в помощи детям.",
              "status": 1,
              "photos": ["https://wanthelp-112222ed0ca0.herokuapp.com/events/img.png", "https://wanthelp-112222ed0ca0.herokuapp.com/events/img_1.png", "https://wanthelp-112222ed0ca0.herokuapp.com/events/img_2.png"],
              "category": "1",
              "createAt": 1692489600000,
              "phone": "+7 (937) 037 37-73",
              "address": "Санкт-Петербург, Кирочная улица,\\nд. 50А, каб. 208",
              "organisation": "Благотворительный Фонд «Счастливый Мир»"
            }, {
              "id": "2",
              "name": "Спонсоры отремонтируют школу-интернат",
              "startDate": 1692662400000,
              "endDate": 1693008000000,
              "description": "Участники и болельщики смогли весело и активно провести время на «Петербургском благотворительном марафоне» и при этом финансово поучаствовать в помощи детям.\\nПри этом финансово поучаствовать в помощи детям. При этом финансово поучаствовать в помощи детям.",
              "status": 1,
              "photos": ["https://wanthelp-112222ed0ca0.herokuapp.com/events/img.png", "https://wanthelp-112222ed0ca0.herokuapp.com/events/img_1.png", "https://wanthelp-112222ed0ca0.herokuapp.com/events/img_2.png"],
              "category": "2",
              "createAt": 1692489600000,
              "phone": "+7 (937) 037 37-73",
              "address": "Санкт-Петербург, Кирочная улица,\\nд. 50А, каб. 208",
              "organisation": "Благотворительный Фонд «Счастливый Мир»"
            }]"""
    }
}
