package com.github.oxarnau.transsectes_app.shared.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import transsectesapp.composeapp.generated.resources.Res
import transsectesapp.composeapp.generated.resources.gepec_edc_oficial
import transsectesapp.composeapp.generated.resources.gepec_edc_oficial_blanc

@Composable
fun BottomAppBarWithLogo() {
    BottomAppBar {
        Image(
            painter = painterResource(if (isSystemInDarkTheme()) Res.drawable.gepec_edc_oficial_blanc else Res.drawable.gepec_edc_oficial),
            contentDescription = null // TODO: use string
        )
    }
}