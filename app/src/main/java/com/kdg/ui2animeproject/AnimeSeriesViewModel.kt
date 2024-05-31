package com.kdg.ui2animeproject


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kdg.ui2animeproject.model.AnimeSeries
import com.kdg.ui2animeproject.model.createAnimeSeries
import com.kdg.ui2animeproject.model.deleteAnimeSeries
import com.kdg.ui2animeproject.model.getAnimeSeries
import com.kdg.ui2animeproject.model.updateAnimeSeries

class AnimeSeriesViewModel : ViewModel() {
    var animeSeries by mutableStateOf(getAnimeSeries())
    var currentIndex by  mutableIntStateOf(0)
    var showDialog by mutableStateOf(false)
    var showDeleteErrorDialog by mutableStateOf(false)
    var isEditing by mutableStateOf(false)

    var title by mutableStateOf("")
    var genre by mutableStateOf("")
    var studio by mutableStateOf("")
    var releaseDate by mutableStateOf("")
    var rating by mutableDoubleStateOf(0.0)
    var completed by mutableStateOf(false)
    var nextId = animeSeries.size+1

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
        val newSeries = AnimeSeries(
            id =nextId,
            title = title,
            releaseDate = releaseDate,
            genre = genre,
            studio = studio,
            averageRating = rating,
            hasCompleted = completed,
            image = R.drawable.placeholderimage
        )
        nextId++
        createAnimeSeries(newSeries)
        animeSeries = getAnimeSeries()

        title = ""
        genre = ""
        studio = ""
        releaseDate = ""
        rating = 0.0
        completed = false
        Log.d("AnimeViewModel", "New Anime added: Title: $title, Id: ${newSeries.id}")
    }

    fun deleteAnimeSeriesById(animeSeriesId: Int) {
        if (animeSeries.size > 1) {
            deleteAnimeSeries(animeSeriesId)
            animeSeries = getAnimeSeries()
            currentIndex = currentIndex.coerceIn(0, animeSeries.size - 1)
            Log.d("AnimeViewModel", "Deleted Anime with Id: $animeSeriesId")
        } else {
            showDeleteErrorDialog = true
            Log.d("AnimeViewModel", "Deletion not allowed: Only one series left.")
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
            image = animeSeries[currentIndex].image
        )
        updateAnimeSeries(updatedAnimeSeries)
        animeSeries = getAnimeSeries()
        isEditing = false
        Log.d("AnimeViewModel", "Changes saved, Anime Id: ${updatedAnimeSeries.id}")
    }

    fun cancelEditing() {
        isEditing = false
        Log.d("AnimeViewModel", "Editing cancelled, Anime Id: ${animeSeries[currentIndex].id}")
    }
}