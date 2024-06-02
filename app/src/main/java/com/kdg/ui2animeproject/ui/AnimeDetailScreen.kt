package com.kdg.ui2animeproject.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.navigation.NavBackStackEntry
import coil.compose.rememberImagePainter
import com.kdg.ui2animeproject.AnimeSeriesViewModel
import com.kdg.ui2animeproject.R
import com.kdg.ui2animeproject.model.AnimeSeries


@Composable
fun AnimeDetailScreen(
    backStackEntry: NavBackStackEntry,
    animeSeriesViewModel: AnimeSeriesViewModel = hiltViewModel()
) {
    val animeSeriesId = backStackEntry.arguments?.getString("animeSeriesId")

    Log.d("AnimeDetailScreen", "Arguments: ${backStackEntry.arguments}")

    if (animeSeriesId == null) {
        Log.d("animedetails", "Invalid animeid")
        return
    }

    val animeSeries = animeSeriesViewModel.animeSeries.find { it.id == animeSeriesId }
    Log.d("animedetails", "Anime series: $animeSeries   AnimeId: $animeSeriesId")

    if (animeSeries == null) {
        Log.d("animedetails", "Anime series not found.")
        return
    }

    animeSeries.let { series ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 30.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = series.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
             Image(
                 painter = rememberImagePainter(series.image),
                 contentDescription = "Image of ${series.title}",
                 contentScale = ContentScale.Crop,
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(bottom = 8.dp)
                     .heightIn(max = 475.dp)
             )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = stringResource(id = R.string.anime_genre) + " : ${series.genre}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = stringResource(id = R.string.anime_rating) + " : ${series.averageRating}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Text(
                text = "Studio: ${series.studio}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = stringResource(id = R.string.release_date) + " : ${series.releaseDate}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Text(
                text = stringResource(id = R.string.anime_characterAmount) + ": ${
                    animeSeriesViewModel.countCharactersByAnimeSeriesId(
                        series.id
                    )
                }",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Row {
                EditAnimeDialog(animeSeriesViewModel,series)
                DeleteCurrentAnimeSeries(animeSeriesViewModel, series.id)
            }


            val characters = animeSeriesViewModel.characters.filter {
                val b = it.animeSerieId.toString() == series.id
                b
            }
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(40.dp),
                contentPadding = PaddingValues(horizontal = 40.dp)
            ) {
                items(characters) { character ->
                    Column(
                        modifier = Modifier
                            .width(150.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = character.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = stringResource(id = R.string.character_powerlevel),
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = ": ${character.powerLevel}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = stringResource(id = R.string.character_specialability) + ": ",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = character.specialAbility,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
            }

            if (animeSeriesViewModel.showDeleteErrorDialog) {
                AlertDialog(
                    onDismissRequest = { animeSeriesViewModel.showDeleteErrorDialog = false },
                    title = { Text(text = "Deletion Not Allowed") },
                    text = { Text("You cannot delete the last anime. At least one must remain :)") },
                    confirmButton = {
                        Button(onClick = { animeSeriesViewModel.showDeleteErrorDialog = false }) {
                            Text("OK")
                        }
                    }
                )
            }


        }
    }
}


@Composable
fun DeleteCurrentAnimeSeries(viewModel: AnimeSeriesViewModel, animeSeriesId: String) {
    Button(
        onClick = { viewModel.deleteAnimeSeriesById(animeSeriesId) },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Series",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun EditAnimeDialog(viewModel: AnimeSeriesViewModel, se: AnimeSeries) {
    FloatingActionButton(
        onClick = { viewModel.startEditing(se) },
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Series",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
    if (viewModel.isEditing) {
        AlertDialog(
            onDismissRequest = { viewModel.cancelEditing() },
            title = { Text("Edit Anime Series") },
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
                Button(onClick = { viewModel.saveChanges() }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { viewModel.cancelEditing() }) {
                    Text("Cancel")
                }
            }
        )
    }
}
