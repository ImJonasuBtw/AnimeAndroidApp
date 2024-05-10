package com.kdg.ui2animeproject.model

import com.kdg.ui2animeproject.Data.AnimeDataSource.charactersList

data class Character(
    val id: Int,
    val name: String,
    val role: String,
    val powerLevel: Double,
    val specialAbility: String,
    val animeSerieId: Int
)


fun getCharacters(): Array
<Character> {
    return charactersList.toTypedArray()
}

fun countCharactersByAnimeSeriesId(animeSeriesId: Int): Int {
    return charactersList.count { it.animeSerieId == animeSeriesId }
}