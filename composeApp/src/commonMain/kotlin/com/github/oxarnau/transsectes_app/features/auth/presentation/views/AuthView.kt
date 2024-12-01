package com.github.oxarnau.transsectes_app.features.auth.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import transsectesapp.composeapp.generated.resources.Res
import transsectesapp.composeapp.generated.resources.imatge_tortuga

// TODO: mantener este diseño o usar el de SplashView
@Composable
fun AuthView(go2SignIn: () -> Unit) {
    val isDarkMode = isSystemInDarkTheme()
    val newColor = if (isDarkMode) null else Color.Black


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        Image(
            painterResource(Res.drawable.imatge_tortuga),
            contentDescription = "Imagen tortuga blanca con fondo transparente", // TODO: i18n
            colorFilter = newColor?.let {
                ColorFilter.tint(
                    color = it,
                    blendMode = BlendMode.SrcIn
                )
            },
            modifier = Modifier.align(Alignment.End).size(300.dp)
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
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.weight(2f))

        Column {
            CustomButton(text = "Create account", goto = {})

            CustomButton(text = "Sign In", goto = go2SignIn)
        }
    }
}


@Composable
fun CustomButton(
    text: String,
    goto: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = goto,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        shape = ButtonDefaults.shape
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            )
        )
    }
}
