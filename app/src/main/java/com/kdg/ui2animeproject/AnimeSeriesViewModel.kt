package com.kdg.ui2animeproject


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kdg.ui2animeproject.data.AnimeRepository
import com.kdg.ui2animeproject.model.AnimeSeries
import com.kdg.ui2animeproject.model.Character
import com.kdg.ui2animeproject.model.NewAnimeSeries
import kotlinx.coroutines.launch

sealed interface AnimeUiState {
    object Loading : AnimeUiState
    object Error : AnimeUiState
    data class Success(
        val animes: List<AnimeSeries>? = null,
        val anime: AnimeSeries? = null,
        val characters: List<Character>? = null,
        val userMessage: String? = null

    ) : AnimeUiState
}

class AnimeSeriesViewModel(private val animeRepository: AnimeRepository) : ViewModel() {
    var animeSeries by mutableStateOf(listOf<AnimeSeries>())
    var characters by mutableStateOf(listOf<Character>())
    var currentIndex by mutableIntStateOf(0)
    var showDialog by mutableStateOf(false)
    var showDeleteErrorDialog by mutableStateOf(false)
    var isEditing by mutableStateOf(false)

    var title by mutableStateOf("")
    var genre by mutableStateOf("")
    var studio by mutableStateOf("")
    var releaseDate by mutableStateOf("")
    var rating by mutableDoubleStateOf(0.0)
    var completed by mutableStateOf(false)
    var nextId = animeSeries.size + 1

    var animeUiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    init {
        getAnimeSeries()
        getCharacters()
    }

    fun getAnimeSeries() {
        viewModelScope.launch {
            try {
                val result: List<AnimeSeries> = animeRepository.getAnimeSeries()
                Log.d("AnimeViewModel", "AnimeSeries fetched: $result")
                if (result.isNotEmpty()) {
                    animeUiState = AnimeUiState.Success(animes = result)
                    updateAnimeSeriesList(result)
                } else {
                    animeUiState = AnimeUiState.Error
                }
            } catch (e: Exception) {
                animeUiState = AnimeUiState.Error
                Log.e("AnimeViewModel", "Error fetching AnimeSeries", e)
            }
        }
    }

    private fun updateAnimeSeriesList(newAnimeSeries: List<AnimeSeries>) {
        animeSeries = newAnimeSeries
    }

    fun getCharacters() {
        viewModelScope.launch {
            try {
                val result: List<Character> = animeRepository.getCharacters()
                Log.d("AnimeViewModel", "Characters fetched: $result")
                if (result.isNotEmpty()) {
                    animeUiState = AnimeUiState.Success(characters = result)
                    updateCharactersList(result)
                } else {
                    animeUiState = AnimeUiState.Error
                }
            } catch (e: Exception) {
                animeUiState = AnimeUiState.Error
                Log.e("AnimeViewModel", "Error fetching Characters", e)
            }
        }
    }

    private fun updateCharactersList(newCharacters: List<Character>) {
        characters = newCharacters
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AnimeApplication)
                val animeRepository = application.container.animeRepository
                AnimeSeriesViewModel(animeRepository = animeRepository)
            }
        }
    }

    fun selectPrevious() {
        currentIndex = (currentIndex - 1 + animeSeries.size) % animeSeries.size
        Log.d("AnimeViewModel", "Previous Anime with Index: $currentIndex")
    }

    fun selectNext() {
        currentIndex = (currentIndex + 1) % animeSeries.size
        Log.d("AnimeViewModel", "Next Anime with Index: $currentIndex")
    }

    fun toggleDialog(show: Boolean) {
        showDialog = show
        Log.d("AnimeViewModel", "Dialog visible : ${if (show) "Ye" else "Nah"}")
    }

    fun createNewAnimeSeries() {
        val newSeries = NewAnimeSeries(
            title = title,
            releaseDate = releaseDate,
            genre = genre,
            studio = studio,
            averageRating = rating,
            hasCompleted = completed,
            // image = R.drawable.placeholderimage
        )

        addAnimeSeries(newSeries)

        title = ""
        genre = ""
        studio = ""
        releaseDate = ""
        rating = 0.0
        completed = false
        Log.d("AnimeViewModel", "New Anime added: Title: $title, Id: ${newSeries.title}")
    }

    private fun addAnimeSeries(newSeries: NewAnimeSeries) {
        viewModelScope.launch {
            try {
                animeRepository.createAnimeSeries(newSeries)
                getAnimeSeries()
            } catch (e: Exception) {
                Log.e("AnimeViewModel", "Error adding AnimeSeries", e)
            }
        }
    }

    fun deleteAnimeSeriesById(animeSeriesId: String) {
        if (animeSeries.size > 1) {
            deleteAnimeSeries(animeSeriesId)
            //currentIndex = currentIndex.coerceIn(0, animeSeries.size - 1)
            Log.d("AnimeViewModel", "Deleted Anime with Id: $animeSeriesId")
        } else {
            showDeleteErrorDialog = true
            Log.d("AnimeViewModel", "Deletion not allowed: Only one series left.")
        }
    }

    private fun deleteAnimeSeries(animeSeriesId: String) {
        viewModelScope.launch {
            try {
                animeRepository.deleteAnimeSeries(animeSeriesId)
                getAnimeSeries()
            } catch (e: Exception) {
                Log.e("AnimeViewModel", "Error deleting AnimeSeries", e)
            }
        }
    }

    fun startEditing() {
        val currentSeries = animeSeries[currentIndex]
        title = currentSeries.title
        genre = currentSeries.genre
        studio = currentSeries.studio
        releaseDate = currentSeries.releaseDate
        rating = currentSeries.averageRating
        completed = currentSeries.hasCompleted
        isEditing = true
        Log.d("AnimeViewModel", "Editing started Anime Id: ${currentSeries.id}")
    }

    fun saveChanges() {
        val updatedAnimeSeries = AnimeSeries(
            id = animeSeries[currentIndex].id,
            title = title,
            releaseDate = releaseDate,
            genre = genre,
            studio = studio,
            averageRating = rating,
            hasCompleted = completed,
            //image = animeSeries[currentIndex].image
        )
        updateAnimeSeries(updatedAnimeSeries)
        isEditing = false
        Log.d("AnimeViewModel", "Changes saved, Anime Id: ${updatedAnimeSeries.id}")
    }

    private fun updateAnimeSeries(updatedAnimeSeries: AnimeSeries) {
        viewModelScope.launch {
            try {
                animeRepository.updateAnineSeries(updatedAnimeSeries)
                getAnimeSeries()
            } catch (e: Exception) {
                Log.e("AnimeViewModel", "Error updating AnimeSeries", e)
            }
        }
    }

    fun cancelEditing() {
        isEditing = false
        Log.d("AnimeViewModel", "Editing cancelled, Anime Id: ")
    }

    fun countCharactersByAnimeSeriesId(id: String): String {
        val count = characters.filter { it.animeSerieId.toString() == id }.size
        return count.toString()
    }
}