package com.example.gameon.views

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.gameon.models.HeadlinesModel
import com.example.gameon.models.HeadlinesModelList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeadlinesView(headlines: HeadlinesModelList) {
    val pagerState = rememberPagerState(pageCount = { headlines.headlinesList.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                Color(0xFF071330), shape = RoundedCornerShape(5.dp)
            )
            .border(
                shape = RoundedCornerShape(5.dp),
                color = Color.Black,
                width = 0.dp
            )
            .padding(bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PageIndicator(pageCount = headlines.headlinesList.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    val currentPage = pagerState.currentPage
                    val totalPages = headlines.headlinesList.size
                    val nextPage = if (currentPage < totalPages - 1) currentPage + 1 else 0
                    scope.launch { pagerState.animateScrollToPage(nextPage) }
                })

        Box(modifier = Modifier.wrapContentSize()) {
            HorizontalPager(
                state = pagerState, modifier = Modifier.wrapContentSize(), pageSpacing = 10.dp
            ) { page ->
                HeadlineItem(headlines.headlinesList[page], Modifier)
            }
        }

    }
}


@Composable
fun HeadlineItem(headline: HeadlinesModel, modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier.background(
            Color(0xFF071330)
        )
    ) {
        val (competitors, startTime, odds) = createRefs()

        Column(modifier = Modifier
            .padding(start = 10.dp, top = 10.dp)
            .constrainAs(competitors) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }

        ) {
            Text(
                text = headline.competitor1Caption,
                color = Color.White,
                fontSize = 15.sp,
                modifier = Modifier
            )
            Text(
                text = headline.competitor2Caption,
                color = Color.White,
                fontSize = 15.sp,
                modifier = Modifier
            )
        }
        Text(text = headline.startTime,
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(end = 10.dp)
                .constrainAs(startTime) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(odds.top)
                })

        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .constrainAs(odds) {
                    top.linkTo(competitors.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
            BettingOddsItem(
                Color.White, Color.Black, "1", "2.35", modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(20.dp))
            BettingOddsItem(
                Color.White, Color.Black, "X", "3.40", modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(20.dp))
            BettingOddsItem(
                Color.White, Color.Black, "2", "2.80", modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.background(
            Color(0xFF071330)
        )
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = modifier)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 8.dp else 6.dp, label = "")
    Box(
        modifier = modifier
            .padding(5.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(if (isSelected) Color.Red else Color.White)
    )
}

@Preview
@Composable
fun PreviewIndicatorDots() {
    IndicatorDots(isSelected = true, modifier = Modifier)
}


@Preview
@Composable
fun PreviewHeadlinesView() {
    HeadlinesView(
        HeadlinesModelList(
            listOf(
                HeadlinesModel(
                    "FirstHome", "FirstAway", "12:30"
                ), HeadlinesModel(
                    "SecondHome", "SecondAway", "13:00"
                ), HeadlinesModel(
                    "ThirdHome", "ThirdAway", "13:30"
                )
            )
        )
    )
}

@Preview
@Composable
fun PreviewHeadlineItem() {
    HeadlineItem(
        HeadlinesModel("competitor1Caption", "competitor2Caption", "startTime"),
        Modifier.fillMaxWidth()
    )
}
