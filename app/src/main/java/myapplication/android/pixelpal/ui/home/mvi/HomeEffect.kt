package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface HomeEffect: MviEffect {

    data class ShowDatesDialog(val date: String): HomeEffect

    data class OpenGameDetailsScreen(
        val gameId: Long,
        val name: String,
        val genres: String,
        val released: String?,
        val image: String?
    ): HomeEffect

    data object OpenAllNextReleasesScreen: HomeEffect

    data object OpenAllCurrentReleasesScreen: HomeEffect

    data object OpenAllTopGamesScreen: HomeEffect
}