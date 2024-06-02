package com.kdg.ui2animeproject.data

import android.util.Log
import com.kdg.ui2animeproject.model.AnimeSeries
import com.kdg.ui2animeproject.model.Character
import com.kdg.ui2animeproject.model.NewAnimeSeries
import com.kdg.ui2animeproject.network.AnimeApiService

interface AnimeRepository {
    suspend fun getAnimeSeries(): List<AnimeSeries>
    suspend fun getCharacters(): List<Character>
    suspend fun createAnimeSeries(animeSeries: NewAnimeSeries)
    suspend fun deleteAnimeSeries(id: String)
    suspend fun updateAnineSeries(animeSeries: AnimeSeries)

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

    override suspend fun createAnimeSeries(animeSeries: NewAnimeSeries) {
        try {
            animeApiService.createAnimeSeries(animeSeries)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteAnimeSeries(id: String) {
        try {
            animeApiService.deleteAnimeSeries(id)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateAnineSeries(animeSeries: AnimeSeries) {
        try {
            Log.d("NetworkAnimeRepository", "Updating AnimeSeries with id: ${animeSeries.id}")
            val response = animeApiService.updateAnimeSeries(animeSeries.id.toString(), animeSeries)
            if (response.isSuccessful) {
                Log.d("NetworkAnimeRepository", "Update successful. Response: ${response.body()}")
                response.body()
            } else {
                Log.e("NetworkAnimeRepository", "Update failed. Response code: ${response.code()}, message: ${response.message()}")
                throw Exception("Error updating AnimeSeries")
            }
        } catch (e: Exception) {
            Log.e("NetworkAnimeRepository", "Exception occurred while updating AnimeSeries", e)
            throw e
        }
    }


}