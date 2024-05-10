package com.kdg.ui2animeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kdg.ui2animeproject.model.*
import com.kdg.ui2animeproject.ui.theme.AnimeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeAppTheme(dynamicColor = false) {
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
fun DisplayAnimeSeries(
    modifier: Modifier = Modifier,
    animeSeriesViewModel: AnimeSeriesViewModel = viewModel()
) {
    val animeSeriesList = animeSeriesViewModel.animeSeries
    val currentAnime = animeSeriesList[animeSeriesViewModel.currentIndex]


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 30.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = currentAnime.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 16.dp),
        )


        Image(
            painter = painterResource(currentAnime.image),
            contentDescription = "Image of ${currentAnime.title}",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .heightIn(max = 475.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(
                    id = R.string.anime_genre,
                    currentAnime.genre
                ) + ": ${currentAnime.genre}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = stringResource(
                    id = R.string.anime_rating,
                    currentAnime.averageRating
                ) + ": ${currentAnime.averageRating}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        Text(
            text = stringResource(id = R.string.anime_characterAmount) + ": ${
                countCharactersByAnimeSeriesId(
                    currentAnime.id
                )
            }",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 5.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    animeSeriesViewModel.selectPrevious()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.button_previous),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            DeleteCurrentAnimeSeries(animeSeriesViewModel)
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    animeSeriesViewModel.selectNext()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.button_next),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        val characters = getCharacters().filter { it.animeSerieId == currentAnime.id }
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

        Row {
            AddAnimeSeriesDialog(animeSeriesViewModel)
            EditAnimeDialog(animeSeriesViewModel)
        }
    }
}

@Composable
fun AddAnimeSeriesDialog(viewModel: AnimeSeriesViewModel) {
    FloatingActionButton(
        onClick = { viewModel.toggleDialog(true) },
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add New Series",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }

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

@Composable
fun DeleteCurrentAnimeSeries(viewModel: AnimeSeriesViewModel) {
    Button(
        onClick = { viewModel.deleteCurrentAnimeSeries() },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Add New Series",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun EditAnimeDialog(viewModel: AnimeSeriesViewModel) {
    FloatingActionButton(
        onClick = { viewModel.startEditing() },
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Add New Series",
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
                        onValueChange = { viewModel.rating = it.toDoubleOrNull() ?: viewModel.rating },
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
