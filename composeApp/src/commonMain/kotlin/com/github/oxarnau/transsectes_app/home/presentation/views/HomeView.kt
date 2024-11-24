package com.github.oxarnau.transsectes_app.home.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import transsectesapp.composeapp.generated.resources.Res
import transsectesapp.composeapp.generated.resources.app_name
import transsectesapp.composeapp.generated.resources.gepec_edc_oficial
import transsectesapp.composeapp.generated.resources.gepec_edc_oficial_blanc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView() {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(Res.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
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

    }

}