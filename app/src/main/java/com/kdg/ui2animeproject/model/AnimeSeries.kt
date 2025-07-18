package com.kdg.ui2animeproject.model

import androidx.annotation.DrawableRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable

data class AnimeSeries(
    val id: String,
    val title: String,
    val releaseDate: String,
    val genre: String,
    val studio: String,
    val averageRating: Double,
    val hasCompleted: Boolean,
    @SerialName("image") val image: String,
)



