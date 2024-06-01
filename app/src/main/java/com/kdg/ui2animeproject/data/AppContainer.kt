package com.kdg.ui2animeproject.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kdg.ui2animeproject.network.AnimeApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit



interface AppContainer {
    val animeRepository: AnimeRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL =
        "http://10.0.2.2:3000/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: AnimeApiService by lazy {
        retrofit.create(AnimeApiService::class.java)
    }

    override val animeRepository: AnimeRepository by lazy{NetworkAnimeRepository(retrofitService)}

}