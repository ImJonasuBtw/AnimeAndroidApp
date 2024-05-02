package com.kdg.ui2animeproject.model

import androidx.annotation.DrawableRes
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

val animeSeriesList = listOf(
    AnimeSeries(
        1,
        "Naruto",
        "2002-10-03",
        "Action",
        "Pierrot",
        8.2,
        true,
        R.drawable.naruto
    ),
    AnimeSeries(
        2,
        "Attack on Titan",
        "2013-04-07",
        "Action",
        "Wit Studio",
        8.8,
        true,
        R.drawable.attackontitan
    ),
    AnimeSeries(
        3,
        "My Hero Academia",
        "2016-04-03",
        "Superhero",
        "Bones",
        8.5,
        false,
        R.drawable.bokuhero
    ),


    )

fun getAnimeSeries(): Array
<AnimeSeries> {
    return animeSeriesList.toTypedArray()
}
