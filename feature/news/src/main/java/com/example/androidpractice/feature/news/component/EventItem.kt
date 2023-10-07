package com.example.androidpractice.feature.news.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.androidpractice.core.designsystem.theme.AppTheme
import com.example.androidpractice.core.designsystem.theme.OfficinaSansExtraBoldSCC
import com.example.androidpractice.core.designsystem.theme.TextStyle10
import com.example.androidpractice.core.designsystem.theme.turtleGreen
import com.example.androidpractice.core.model.event.Event
import com.example.androidpractice.feature.news.utils.getEventDateText
import kotlinx.datetime.LocalDate
import com.example.androidpractice.core.designsystem.R as designR

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun EventItem(
    event: Event,
    onEventClick: (eventId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = {
            onEventClick(event.id)
        },
        elevation = 4.dp,
        modifier = modifier
    ) {
        Column {
            val imageHeight = 222.dp
            AsyncImage(
                model = event.imagePreview,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .drawWithContent {
                        drawContent()
                        drawCircle(
                            brush = Brush.radialGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.White
                                ),
                                radius = size.width,
                                center = Offset(size.width / 2, size.height - size.width)
                            ),
                            center = Offset(size.width / 2, 0f),
                            radius = size.width
                        )
                    }
            )
            Column(
                modifier = Modifier
                    .offset(y = (-10).dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = event.title,
                    style = OfficinaSansExtraBoldSCC,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp).semantics { testTag = "title_${event.id}" },
                )
                Image(
                    painter = painterResource(id = designR.drawable.ic_event_decor),
                    modifier = Modifier.padding(top = dimensionResource(id = designR.dimen.spacing_xs)),
                    contentDescription = null
                )
            }
            Text(
                text = event.subtitle,
                style = TextStyle10,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = designR.dimen.spacing_m))
                    .background(turtleGreen)
                    .heightIn(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = designR.drawable.ic_calendar),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = designR.dimen.spacing_xxs)))
                Text(
                    text = getEventDateText(LocalContext.current, event.startDate, event.endDate),
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colors.onPrimary
                    ),
                    modifier = Modifier.padding(start = dimensionResource(id = designR.dimen.spacing_xxs))
                )
            }
        }
    }
}

@Preview(device = "id:Nexus S")
@Preview(showBackground = true, device = "id:pixel_7_pro")
@Composable
private fun PreviewEventItem() {
    AppTheme {
        EventItem(
            event = Event(
                id = "viris",
                title = "dolorum",
                subtitle = "sale",
                description = "dolorum",
                sponsor = "prodesset",
                startDate = LocalDate(2023, 9, 4),
                endDate = LocalDate(2023, 9, 10),
                createAt = LocalDate(2023, 9, 3),
                address = "dolores",
                phoneNumber = "(789) 825-4392",
                email = "romeo.carpenter@example.com",
                siteUrl = "http://www.bing.com/search?q=indoctum",
                imagePreview = "https://wanthelp-112222ed0ca0.herokuapp.com/events/img_1.png",
                imageUrls = listOf("https://wanthelp-112222ed0ca0.herokuapp.com/events/img_1.png"),
                category = "feugiat"
            ),
            onEventClick = {},
            modifier = Modifier
        )
    }
}
