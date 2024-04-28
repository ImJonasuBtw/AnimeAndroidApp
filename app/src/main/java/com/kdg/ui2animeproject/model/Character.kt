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
    Character(1, "Naruto Uzumaki", "Protagonist", 95.5, "Rasengan", 1),
)

fun getCharacters(): Array
<Character> {
    return charactersList.toTypedArray()
}

fun countCharactersByAnimeSeriesId(animeSeriesId: Int): Int {
    return charactersList.count { it.animeSerieId == animeSeriesId }
}