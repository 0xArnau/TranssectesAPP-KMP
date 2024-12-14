package com.github.oxarnau.transsectes_app.features.transect.presentation.records.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.intents.RecordsIntent
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.viewmodels.RecordsViewModel
import com.github.oxarnau.transsectes_app.shared.presentation.components.CustomCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransectsListView(
    innerPadding: PaddingValues,
    viewModel: RecordsViewModel,
    intent: RecordsIntent
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.onIntent(intent)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        itemsIndexed(state.records) { index, record ->
            CustomCard(
                leadingText = record.localityAreaFirst ?: "",
                title = record.createdAt.toString(),
                subtitle = record.observations,
                onClick = {
                    viewModel.onIntent(RecordsIntent.onTransectClick(index))
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}