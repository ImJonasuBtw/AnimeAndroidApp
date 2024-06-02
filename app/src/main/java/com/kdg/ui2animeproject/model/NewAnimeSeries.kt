package com.kdg.ui2animeproject.model

import kotlinx.serialization.Serializable

@Serializable
data class NewAnimeSeries(  val title: String,
                            val releaseDate: String,
                            val genre: String,
                            val studio: String,
                            val averageRating: Double,
                            val hasCompleted: Boolean,
    // @DrawableRes val image: Int
    )
