package com.kdg.ui2animeproject

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kdg.ui2animeproject.ui.AnimeDetailScreen
import com.kdg.ui2animeproject.ui.StartScreen


enum class AnimeScreen(@StringRes val title: Int, val route: String) {
    Start(title = R.string.app_name, route = "start"),
    AnimeDetail(title = R.string.anime_detail, route = "AnimeDetail/{animeSeriesId}")
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeAppBar(
    currentScreen: AnimeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.button_previous)
                    )
                }
            }
        }
    )
}

@Composable
fun AnimeNav(
    animeSeriesViewModel: AnimeSeriesViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: AnimeScreen.Start.route
    val currentScreen = AnimeScreen.values().find { it.route == currentRoute } ?: AnimeScreen.Start


    Scaffold(
        topBar = {
            AnimeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AnimeScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = AnimeScreen.Start.name) {
                StartScreen(animeUiState = animeSeriesViewModel.animeUiState ,navController = navController, animeSeriesViewModel = animeSeriesViewModel)
            }
            composable(
                route = "AnimeDetail/{animeSeriesId}",
                arguments = listOf(navArgument("animeSeriesId") { type = NavType.StringType })
            ) { backStackEntry ->
                AnimeDetailScreen(
                    backStackEntry = backStackEntry,
                    animeSeriesViewModel = animeSeriesViewModel
                )
            }
        }
    }
}