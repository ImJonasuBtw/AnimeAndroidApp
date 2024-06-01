package com.kdg.ui2animeproject.data

import com.kdg.ui2animeproject.model.AnimeSeries
import com.kdg.ui2animeproject.model.Character
import com.kdg.ui2animeproject.network.AnimeApiService

interface AnimeRepository {
    suspend fun getAnimeSeries(): List<AnimeSeries>
    suspend fun getCharacters(): List<Character>
    suspend fun createAnimeSeries(animeSeries: AnimeSeries)

}


class NetworkAnimeRepository(val animeApiService: AnimeApiService) : AnimeRepository {
    override suspend fun getAnimeSeries(): List<AnimeSeries> {
        try {
            return animeApiService.getAnimeSeries()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCharacters(): List<Character> {
        try {
            return animeApiService.getCharacters()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createAnimeSeries(animeSeries: AnimeSeries) {
        try {
            animeApiService.createAnimeSeries(animeSeries)
        } catch (e: Exception) {
            throw e
        }
    }


}