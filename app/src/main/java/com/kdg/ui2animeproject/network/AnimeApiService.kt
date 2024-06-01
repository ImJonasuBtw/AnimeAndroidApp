package com.kdg.ui2animeproject.network

import com.kdg.ui2animeproject.model.AnimeSeries
import com.kdg.ui2animeproject.model.Character
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimeApiService {
    @GET("animeSeries")
    suspend fun getAnimeSeries(): List<AnimeSeries>

    @GET("characters")
    suspend fun getCharacters(): List<Character>

    @POST("animeSeries")
    suspend fun createAnimeSeries(
        @Body animeSeries: AnimeSeries
    ): Response<AnimeSeries>
}