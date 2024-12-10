package com.github.oxarnau.transsectes_app.features.transect.presentation.records.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.viewmodels.RecordsViewModel
import com.github.oxarnau.transsectes_app.shared.presentation.components.CustomCard
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTransectsView(
    innerPadding: PaddingValues,
    viewModel: RecordsViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        items(state.records) { record ->
            println("record.localityAreaFirst: ${record.localityAreaFirst}")
            CustomCard(
                leadingText = record.localityAreaFirst ?: "",
                title = record.createdAt.toString(),
                subtitle = record.observations,
                onClick = { println("Created by ${record.createdBy} at ${record.createdAt}") },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}