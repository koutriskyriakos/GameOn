package com.example.gameon.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameon.models.GamesModel
import com.example.gameon.models.GamesModelList
import com.example.gameon.transformers.Timer

@Composable
fun GamesView(games: GamesModelList) {
    var isExpanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 15.dp)
            .background(Color(0xFF0E1111))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .border(
                    shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp),
                    color = Color.Gray,
                    width = 2.dp
                )
                .background(
                    Color.Gray,
                    shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
                )
                .padding(8.dp)
                .clickable {
                    isExpanded = !isExpanded
                }
        ) {
            Icon(
                imageVector = Icons.TwoTone.ArrowDropDown,
                contentDescription = "arrow",
                modifier = Modifier.padding(end = 8.dp),
            )
            Text(
                text = "Ποδόσφαιρο",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            )
        }

        AnimatedVisibility(visible = isExpanded) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(games.gamesList) { item ->
                    CardItem(item)
                }
            }
        }
    }

}

@Composable
fun CardItem(game: GamesModel) {
    val elapsedTime by game.timer.elapsedTime.collectAsState()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(
                Color.DarkGray,
                shape = RoundedCornerShape(5.dp)
            )
            .border(
                shape = RoundedCornerShape(5.dp),
                color = Color.Gray,
                width = 0.dp
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                TeamWithScore(game.competitor1)
                Spacer(modifier = Modifier.height(10.dp))
                TeamWithScore(game.competitor2)
            }
            Text(
                text = elapsedTime,
                color = Color.White,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
        ) {
            BettingOddsItem(
                Color.Gray, Color.Black, "1", "2.35", modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(20.dp))
            BettingOddsItem(
                Color.Gray, Color.Black, "X", "3.25", modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(20.dp))
            BettingOddsItem(
                Color.Gray, Color.Black, "2", "2.30", modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TeamWithScore(competitor: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = competitor,
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f),
            softWrap = true
        )
        Spacer(modifier = Modifier.weight(0.2f))
        Text(
            text = "0",
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.weight(0.2f),
            softWrap = true
        )
    }
}

@Preview
@Composable
fun GamesViewPreview() {
    GamesView(
        GamesModelList(
            listOf(
                GamesModel("competitor1", "competitor2", Timer("6:23")),
                GamesModel("competitor1", "competitor2", Timer("12:23")),
                GamesModel("competitor1", "competitor2", Timer("58:23")),
                GamesModel("competitor1", "competitor2", Timer("86:23"))
            )
        )
    )
}

@Preview
@Composable
fun CardItemPreview() {
    CardItem(GamesModel("competitor1", "competitor2", Timer("6:23")))
}

@Preview
@Composable
fun TeamWithScorePreview() {
    TeamWithScore("competitor1")
}