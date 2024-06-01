package com.kdg.ui2animeproject

import android.app.Application
import com.kdg.ui2animeproject.data.AppContainer
import com.kdg.ui2animeproject.data.DefaultAppContainer

class AnimeApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}