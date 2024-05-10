package com.kdg.ui2animeproject.model

import androidx.annotation.DrawableRes
import com.kdg.ui2animeproject.Data.AnimeDataSource.animeSeriesList
import com.kdg.ui2animeproject.R

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
