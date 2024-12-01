package com.github.oxarnau.transsectes_app.features.splash.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.oxarnau.transsectes_app.shared.components.WaveShape
import org.jetbrains.compose.resources.painterResource
import transsectesapp.composeapp.generated.resources.Res
import transsectesapp.composeapp.generated.resources.gepec_edc_oficial_blanc
import transsectesapp.composeapp.generated.resources.imatge_tortuga

// TODO: dark mode
@Composable
fun SplashView(goto: () -> Unit) {
    val backgroundColor = if (isSystemInDarkTheme()) {
        Color.Gray.copy(0.5f)
    } else {
        Color.White
    }

    val circleColor = if (isSystemInDarkTheme()) {
        Color.Yellow.copy(0.5f)
    } else {
        Color.Yellow
    }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000)
        goto()
    }

    Box {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .padding(10.dp)
//                    .size(LocalConfiguration.current.screenWidthDp.dp / 2)
                    .size(200.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .background(color = circleColor)
            )

            Text(
                text = "Transsectes APP",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.safeContent)
                    .align(Alignment.TopCenter)
            )
        }

        WaveWithTurtle()
    }
}

@Composable
fun WaveWithTurtle(modifier: Modifier = Modifier) {
//    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenHeight = 800.dp

    Box {
        WaveShape()
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Image(
                painter = painterResource(Res.drawable.imatge_tortuga),
                contentDescription = "Imagen tortuga blanca con fondo transparente", // TODO: i18n
                modifier = Modifier
                    .align(Alignment.End)
                    .size(screenHeight / 2)

            )
            Image(
                painter = painterResource(Res.drawable.gepec_edc_oficial_blanc),
                contentDescription = "Gepec-Edc logo en blanco",
                modifier = Modifier.padding(bottom = 28.dp)
            )
        }
    }
}
