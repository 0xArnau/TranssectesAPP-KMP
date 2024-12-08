package com.github.oxarnau.transsectes_app.features.transect.presentation.records.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.viewmodels.RecordsViewModel
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
            // TODO: use a card
            Column {
                Text(
                    text = "Created by: ${record.createdBy}",
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Created At: ${record.createdAt}",
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Locality: ${record.localityAreaFirst}",
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Observations: ${record.observations}",
                    modifier = Modifier.padding(16.dp)
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = androidx.compose.ui.graphics.Color.Gray
            )
        }
    }
}