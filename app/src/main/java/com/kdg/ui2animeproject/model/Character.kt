package com.kdg.ui2animeproject.model

data class Character(
    val id: Int,
    val name: String,
    val role: String,
    val powerLevel: Double,
    val specialAbility: String,
    val animeSerieId: Int
)

val charactersList = listOf(
    Character(
        1, "Naruto Uzumaki", "Protagonist", 95.5, "Rasengan", 1
    ),
    Character(
        2, "Sasuke Uchiha", "Rival", 95.2, "Sharingan", 1
    ),
    Character(
        3, "Eren Yeager", "Protagonist", 92.3, "Titan Transformation", 2
    ),
    Character(
        4, "Izuku Midoriya", "Protagonist", 88.1, "One For All", 3
    )
)

fun getCharacters(): Array
<Character> {
    return charactersList.toTypedArray()
}

fun countCharactersByAnimeSeriesId(animeSeriesId: Int): Int {
    return charactersList.count { it.animeSerieId == animeSeriesId }
}