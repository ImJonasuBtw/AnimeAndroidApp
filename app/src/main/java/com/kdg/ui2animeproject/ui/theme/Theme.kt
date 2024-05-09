 package com.kdg.ui2animeproject.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

 private val DarkAnimeColorScheme = darkColorScheme(
     primary = VividPurple,
     secondary = VividCyan,
     tertiary = VividPink,
     background = AlmostBlack,
     surface = DarkGrey,
     onPrimary = Color.White,
     onSecondary = Color.White,
     onTertiary = Color.White,
     onBackground = Color.White,
     onSurface = Color.White
 )

 private val LightAnimeColorScheme = lightColorScheme(
     primary = SoftPurple,
     secondary = SoftCyan,
     tertiary = SoftPink,
     background = AlmostPinkPink,
     surface = VeryLightGrey,
     onPrimary = Color.Black,
     onSecondary = Color.Black,
     onTertiary = Color.Black,
     onBackground = Color.Black,
     onSurface = Color.Black
 )

 @Composable
 fun AnimeAppTheme(
     darkTheme: Boolean = isSystemInDarkTheme(),
     dynamicColor: Boolean = true,
     content: @Composable () -> Unit
 ) {
     val colorScheme = when {
         dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
             val context = LocalContext.current
             if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
         }
         darkTheme -> DarkAnimeColorScheme
         else -> LightAnimeColorScheme
     }
     val view = LocalView.current
     if (!view.isInEditMode) {
         SideEffect {
             val window = (view.context as Activity).window
             window.statusBarColor = colorScheme.primary.toArgb()
             WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
         }
     }

     MaterialTheme(
         colorScheme = colorScheme,
         typography = Typography,
         content = content
     )
 }