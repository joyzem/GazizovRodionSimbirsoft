package com.example.androidpractice.feature.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androidpractice.core.designsystem.component.CenteredTopAppBar
import com.example.androidpractice.core.model.event.Event
import com.example.androidpractice.feature.news.component.EventItem
import com.example.androidpractice.core.designsystem.R as designR

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun NewsScreen(
    news: List<Event>?,
    onEventClick: (eventId: String) -> Unit,
    onFiltersClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = { Text(stringResource(id = R.string.news)) },
                actions = {
                    IconButton(onClick = onFiltersClick, modifier = Modifier.size(24.dp)) {
                        Icon(
                            painter = painterResource(id = designR.drawable.ic_filter),
                            contentDescription = stringResource(id = R.string.filter)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        news?.let { events ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = designR.dimen.spacing_xs)),
                contentPadding = PaddingValues(
                    start = dimensionResource(id = designR.dimen.spacing_xs),
                    top = dimensionResource(id = designR.dimen.spacing_xs),
                    end = dimensionResource(id = designR.dimen.spacing_xs),
                    bottom = dimensionResource(id = designR.dimen.spacing_m),
                )
            ) {
                items(events, key = { it.id }) { event ->
                    EventItem(
                        event = event,
                        onEventClick = onEventClick,
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        } ?: CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}
