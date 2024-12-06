package com.github.oxarnau.transsectes_app.shared.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun WaveShape() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Draw the wave shape
            drawWave(this, color = Color.Blue)
        }
    }
}

fun drawWave(drawScope: DrawScope, color: Color) {
    val canvasWidth = drawScope.size.width
    val canvasHeight = drawScope.size.height

    // Create a Path object
    val path = Path().apply {
        moveTo(0f, canvasHeight / 2)
        quadraticTo(
            canvasWidth / 4, canvasHeight / 1.5f,
            canvasWidth / 2, canvasHeight / 2
        )
        quadraticTo(
            3 * canvasWidth / 4, canvasHeight / 3,
            canvasWidth, canvasHeight / 2
        )
        lineTo(canvasWidth, canvasHeight)
        lineTo(0f, canvasHeight)
        close()
    }

    // Draw the path on the canvas
    drawScope.drawPath(
        path = path,
        color = color
    )
}
