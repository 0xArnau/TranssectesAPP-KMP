package com.github.oxarnau.transsectes_app.home.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
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
import transsectesapp.composeapp.generated.resources.walk

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView() {
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
        ScrollContent(innerPadding)
    }

}

@Composable
fun ScrollContent(innerPadding: PaddingValues) {

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
                TODO()
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
                TODO()
            }
        }
        item {
            TextImageButton(
                resourceId = Res.drawable.contact,
                resourceDescription = stringResource(Res.string.home_contact),
                text = stringResource(Res.string.home_contact),
                size = 96.dp
            ) {
                TODO()
            }
        }
    }
}


@Composable
fun ImageTextButton(
    resourceId: DrawableResource,
    resourceDescription: String,
    text: String,
    modifier: Modifier = Modifier,
    size: Dp = 128.dp,
    onClick: () -> Unit,
) {
    val contentColor =
        if (isSystemInDarkTheme()) Color.White else Color.Black


    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = contentColor
        ),
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            Image(
                painter = painterResource(resourceId),
                contentDescription = resourceDescription,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(size)

            )

            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun TextImageButton(
    resourceId: DrawableResource,
    resourceDescription: String,
    text: String,
    modifier: Modifier = Modifier,
    size: Dp = 128.dp,
    onClick: () -> Unit,
) {
    val contentColor =
        if (isSystemInDarkTheme()) Color.White else Color.Black


    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = contentColor
        ),
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(end = 16.dp)
            )

            Image(
                painter = painterResource(resourceId),
                contentDescription = resourceDescription,
                modifier = Modifier
                    .size(size)
            )

        }
    }
}