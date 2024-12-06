package com.github.oxarnau.transsectes_app.shared.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    text: String,
    goto: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = goto,
        modifier = modifier

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
