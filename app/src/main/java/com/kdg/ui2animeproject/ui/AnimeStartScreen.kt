package com.kdg.ui2animeproject.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.kdg.ui2animeproject.AnimeSeriesViewModel
import com.kdg.ui2animeproject.AnimeUiState
import com.kdg.ui2animeproject.R
import com.kdg.ui2animeproject.model.AnimeSeries

@Composable
fun StartScreen(
    animeUiState: AnimeUiState,
    navController: NavController,
    animeSeriesViewModel: AnimeSeriesViewModel = hiltViewModel()
) {
    when (animeUiState) {
        is AnimeUiState.Loading -> Text("Loading")
        is AnimeUiState.Error -> Text("Error")
        is AnimeUiState.Success -> animeUiState.animes.let {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    var anime : List<AnimeSeries> = emptyList()
                    if (it != null) {
                        anime = it
                    };
                    items(anime) { animeSeries ->
                        AnimeSeriesItem(animeSeries, onClick = {
                            navController.navigate("AnimeDetail/${animeSeries.id}")
                        })
                    }
                }

                FloatingActionButton(
                    onClick = { animeSeriesViewModel.toggleDialog(true) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add New Series",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                if (animeSeriesViewModel.showDialog) {
                    AddAnimeSeriesDialog(animeSeriesViewModel)
                }
            }
        }
    }

}

@Composable
fun AnimeSeriesItem(animeSeries: AnimeSeries, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(animeSeries.image),
                contentDescription = "Image of ${animeSeries.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = animeSeries.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(id = R.string.anime_genre) +" : ${animeSeries.genre}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.anime_rating)+" : ${animeSeries.averageRating}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun AddAnimeSeriesDialog(viewModel: AnimeSeriesViewModel) {
    if (viewModel.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.toggleDialog(false) },
            title = { Text("Add New Anime Series") },
            text = {
                Column {
                    TextField(
                        value = viewModel.title,
                        onValueChange = { viewModel.title = it },
                        label = { Text("Title") }
                    )
                    TextField(
                        value = viewModel.genre,
                        onValueChange = { viewModel.genre = it },
                        label = { Text("Genre") }
                    )
                    TextField(
                        value = viewModel.studio,
                        onValueChange = { viewModel.studio = it },
                        label = { Text("Studio") }
                    )
                    TextField(
                        value = viewModel.releaseDate,
                        onValueChange = { viewModel.releaseDate = it },
                        label = { Text("Release Date") }
                    )
                    TextField(
                        value = viewModel.rating.toString(),
                        onValueChange = {
                            viewModel.rating = it.toDoubleOrNull() ?: viewModel.rating
                        },
                        label = { Text("Average Rating") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = viewModel.completed,
                            onCheckedChange = { viewModel.completed = it }
                        )
                        Text("Completed")
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.createNewAnimeSeries()
                        viewModel.toggleDialog(false)
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(onClick = { viewModel.toggleDialog(false) }) {
                    Text("Cancel")
                }
            }
        )
    }
}
