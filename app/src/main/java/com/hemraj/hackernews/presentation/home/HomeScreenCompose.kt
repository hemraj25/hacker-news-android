package com.hemraj.hackernews.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.hemraj.hackernews.domain.HackerNews

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    val list = mutableListOf<HackerNews>().apply {
        add(HackerNews("Hacked 1", "News Hacked", "Mohit", "www.test.com"))
        add(HackerNews("Hacked 2", "News Hacked", "Mohit", "www.test.com"))
        add(HackerNews("Hacked 3", "News Hacked", "Mohit", "www.test.com"))
        add(HackerNews("Hacked 4", "News Hacked", "Mohit", "www.test.com"))
        add(HackerNews("Hacked 5", "News Hacked", "Mohit", "www.test.com"))
        add(HackerNews("Hacked 6", "News Hacked", "Mohit", "www.test.com"))
        add(HackerNews("Hacked 7", "News Hacked", "Mohit", "www.test.com"))
        add(HackerNews("Hacked 8", "News Hacked", "Mohit", "www.test.com"))
        add(HackerNews("Hacked 9", "News Hacked", "Mohit", "www.test.com"))
        add(HackerNews("Hacked 10", "News Hacked", "Mohit", "www.test.com"))
    }

    ShowNewsList(data = list) {
    }
}

@Composable
fun PlayLottieAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("smoothymon_typing.json")
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition = composition, progress = {
            progress
        })
    }
}

@Composable
fun ShowNewsList(data: List<HackerNews>, onNewsClick: (HackerNews) -> Unit) {
    val dataState by remember { mutableStateOf(data) }
    LazyColumn(content = {
        items(dataState) {
            HackerNewsItemView(it, onNewsClick)
        }
    })
}

@Composable
fun HackerNewsItemView(hackerNews: HackerNews, onNewsClick: (HackerNews) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier.padding(5.dp)
    )
    {
        Column(
            modifier = Modifier
                .clickable {
                    onNewsClick.invoke(hackerNews)
                }
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.Center
        )
        {
            Text(
                text = hackerNews.title,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 5.dp),
                maxLines = 2
            )
            if (hackerNews.commentText.isNotEmpty()) {
                Text(
                    text = hackerNews.commentText,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 3.dp),
                    maxLines = 4
                )
            }
            if (hackerNews.url.isNotEmpty()) {
                Text(
                    text = hackerNews.url,
                    color = Color.Magenta,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}