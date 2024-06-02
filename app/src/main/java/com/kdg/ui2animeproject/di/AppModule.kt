package com.kdg.ui2animeproject.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kdg.ui2animeproject.data.AnimeRepository
import com.kdg.ui2animeproject.data.NetworkAnimeRepository
import com.kdg.ui2animeproject.network.AnimeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val BASE_URL =
        "http://10.0.2.2:3000/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideAnimeApiService(retrofit: Retrofit): AnimeApiService {
        return retrofit.create(AnimeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAnimeRepository(retrofitService: AnimeApiService): AnimeRepository {
        return NetworkAnimeRepository(retrofitService)
    }

}