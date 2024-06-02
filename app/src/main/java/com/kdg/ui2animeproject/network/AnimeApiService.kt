package com.kdg.ui2animeproject.network

import com.kdg.ui2animeproject.model.AnimeSeries
import com.kdg.ui2animeproject.model.Character
import com.kdg.ui2animeproject.model.NewAnimeSeries
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AnimeApiService {
    @GET("animeSeries")
    suspend fun getAnimeSeries(): List<AnimeSeries>

    @GET("characters")
    suspend fun getCharacters(): List<Character>

    @POST("animeSeries")
    suspend fun createAnimeSeries(
        @Body animeSeries: NewAnimeSeries
    ): Response<AnimeSeries>

    @DELETE("animeSeries/{id}")
    suspend fun deleteAnimeSeries(
        @Path("id") id: String
    ): Response<Unit>

    @PUT("animeSeries/{id}")
    suspend fun updateAnimeSeries(
        @Path("id") id: String,
        @Body animeSeries: AnimeSeries
    ): Response<AnimeSeries>
}