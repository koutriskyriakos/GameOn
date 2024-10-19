package com.example.gameon.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Composable
fun ErrorScreen(errorMessage: String) {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = errorMessage,
        fontSize = 80.sp,
        color = Color.Red
    )
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen("Error message")
}