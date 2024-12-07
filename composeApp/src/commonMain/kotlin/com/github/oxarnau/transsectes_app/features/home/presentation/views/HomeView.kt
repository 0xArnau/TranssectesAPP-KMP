package com.github.oxarnau.transsectes_app.features.home.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.oxarnau.transsectes_app.shared.presentation.components.ImageTextButton
import com.github.oxarnau.transsectes_app.shared.presentation.components.TextImageButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import transsectesapp.composeapp.generated.resources.Res
import transsectesapp.composeapp.generated.resources.app_name
import transsectesapp.composeapp.generated.resources.book
import transsectesapp.composeapp.generated.resources.contact
import transsectesapp.composeapp.generated.resources.gepec_edc_oficial
import transsectesapp.composeapp.generated.resources.gepec_edc_oficial_blanc
import transsectesapp.composeapp.generated.resources.home_contact
import transsectesapp.composeapp.generated.resources.home_how_to
import transsectesapp.composeapp.generated.resources.home_records
import transsectesapp.composeapp.generated.resources.home_start
import transsectesapp.composeapp.generated.resources.route
import transsectesapp.composeapp.generated.resources.settings
import transsectesapp.composeapp.generated.resources.walk

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navigate2HowTo: () -> Unit,
    navigate2Records: () -> Unit,
    navigate2Contact: () -> Unit,
    navigate2Settings: () -> Unit,
) {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        stringResource(Res.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navigate2Settings()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(Res.string.settings)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar {
                Image(
                    painter = painterResource(if (isSystemInDarkTheme()) Res.drawable.gepec_edc_oficial_blanc else Res.drawable.gepec_edc_oficial),
                    contentDescription = null // TODO: use string
                )
            }
        }
    ) { innerPadding ->
        ScrollContent(
            innerPadding,
            navigate2HowTo,
            navigate2Records,
            navigate2Contact
        )
    }

}

@Composable
fun ScrollContent(
    innerPadding: PaddingValues,
    navigate2HowTo: () -> Unit,
    navigate2Records: () -> Unit,
    navigate2Contact: () -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = innerPadding
    ) {
        item {
            ImageTextButton(
                resourceId = Res.drawable.walk,
                resourceDescription = stringResource(Res.string.home_how_to),
                text = stringResource(Res.string.home_how_to),
                size = 116.dp
            ) {
                navigate2HowTo()
            }
        }
        item {
            TextImageButton(
                resourceId = Res.drawable.route,
                resourceDescription = stringResource(Res.string.home_start),
                text = stringResource(Res.string.home_start),
                size = 164.dp
            ) {
                TODO()
            }
        }
        item {
            ImageTextButton(
                resourceId = Res.drawable.book,
                resourceDescription = stringResource(Res.string.home_records),
                text = stringResource(Res.string.home_records)
            ) {
                navigate2Records()
            }
        }
        item {
            TextImageButton(
                resourceId = Res.drawable.contact,
                resourceDescription = stringResource(Res.string.home_contact),
                text = stringResource(Res.string.home_contact),
                size = 96.dp
            ) {
                navigate2Contact()
            }
        }
    }
}



