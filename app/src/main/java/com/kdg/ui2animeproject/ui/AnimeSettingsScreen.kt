package com.kdg.ui2animeproject.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.kdg.ui2animeproject.AnimeSeriesViewModel
import com.kdg.ui2animeproject.R

@Composable
fun AnimeSettingsScreen(
    animeSeriesViewModel: AnimeSeriesViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = stringResource(id = R.string.settings_Dark_Theme))
        Switch(
            checked = animeSeriesViewModel.isDarkTheme.value,
            onCheckedChange = { animeSeriesViewModel.isDarkTheme.value = it }
        )
        Text(text = stringResource(id = R.string.settings_Additional_Info))
        Switch(
            checked = animeSeriesViewModel.isShowingAdditonalInfo.value,
            onCheckedChange = { animeSeriesViewModel.isShowingAdditonalInfo.value = it }
        )
    }
}