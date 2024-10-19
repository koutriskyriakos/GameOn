package com.example.gameon.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BettingOddsItem(
    backgroundColor: Color,
    textColor: Color,
    textOne: String,
    textTwo: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .border(
                shape = RoundedCornerShape(5.dp),
                color = Color.Gray,
                width = 2.dp,
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(5.dp)
            )
            .height(35.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = textOne, modifier = Modifier.padding(start = 10.dp), color = textColor
        )
        Text(
            text = textTwo, color = textColor, modifier = Modifier.padding(end = 10.dp)
        )
    }
}

@Preview
@Composable
fun PreviewBettingOddsItem(
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    textOne: String = "1",
    textTwo: String = "2.35"
) {
    BettingOddsItem(
        backgroundColor,
        textColor,
        textOne,
        textTwo,
        modifier = Modifier.fillMaxWidth()
    )
}