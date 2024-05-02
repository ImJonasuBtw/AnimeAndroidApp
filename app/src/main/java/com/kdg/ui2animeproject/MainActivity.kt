package com.kdg.ui2animeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kdg.ui2animeproject.model.countCharactersByAnimeSeriesId
import com.kdg.ui2animeproject.model.getAnimeSeries
import com.kdg.ui2animeproject.ui.theme.UI2AnimeProjectTheme
import java.nio.channels.FileChannel.MapMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UI2AnimeProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DisplayAnimeSeries()
                }
            }
        }
    }
}


@Preview
@Composable
fun DisplayAnimeSeries(modifier: Modifier = Modifier) {
    val animeSeriesList = getAnimeSeries()
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentAnime = animeSeriesList[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 30.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(currentAnime.image),
            contentDescription = "Image of ${currentAnime.title}",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .heightIn(max = 500.dp)
        )
        Text(
            text = "Title: ${currentAnime.title}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Genre: ${currentAnime.genre}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Rating: ${currentAnime.averageRating}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = "Number of Characters: ${countCharactersByAnimeSeriesId(currentAnime.id)}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

                    Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { currentIndex = (currentIndex - 1).coerceAtLeast(0) },
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Text(text = "Previous")
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(
                onClick = { currentIndex = (currentIndex + 1).coerceAtMost(animeSeriesList.size - 1) },
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Text(text = "Next")
            }
        }
    }
}


