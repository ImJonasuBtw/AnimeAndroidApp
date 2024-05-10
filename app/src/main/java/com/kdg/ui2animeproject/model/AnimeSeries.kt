package com.kdg.ui2animeproject.model

import androidx.annotation.DrawableRes
import com.kdg.ui2animeproject.Data.AnimeDataSource.animeSeriesList

data class AnimeSeries(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val genre: String,
    val studio: String,
    val averageRating: Double,
    val hasCompleted: Boolean,
    @DrawableRes val image: Int
)


fun getAnimeSeries(): Array
<AnimeSeries> {
    return animeSeriesList.toTypedArray()
}

fun createAnimeSeries(animeSeries: AnimeSeries) {
    animeSeriesList.add(animeSeries)
}

fun readAnimeSeries(id: Int): AnimeSeries? {
    return animeSeriesList.find { it.id == id }
}

fun updateAnimeSeries(updatedAnimeSeries: AnimeSeries) {
    val index = animeSeriesList.indexOfFirst { it.id == updatedAnimeSeries.id }
    if (index != -1) {
        animeSeriesList[index] = updatedAnimeSeries
    }
}

fun deleteAnimeSeries(id: Int) {
    animeSeriesList.removeAll { it.id == id }
}
