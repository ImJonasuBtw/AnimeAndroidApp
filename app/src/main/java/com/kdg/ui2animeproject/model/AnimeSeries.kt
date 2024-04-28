package com.kdg.ui2animeproject.model

data class AnimeSeries(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val genre: String,
    val studio: String,
    val averageRating: Double,
    val hasCompleted: Boolean,
    val image: String
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
        "https://i.pinimg.com/564x/3a/8c/63/3a8c63737ae2d94f9d4f09f477e3df34.jpg"
    ),

    )

fun getAnimeSeries(): Array
<AnimeSeries> {
    return animeSeriesList.toTypedArray()
}
