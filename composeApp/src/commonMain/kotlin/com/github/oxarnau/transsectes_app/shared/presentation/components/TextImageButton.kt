package com.github.oxarnau.transsectes_app.shared.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

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