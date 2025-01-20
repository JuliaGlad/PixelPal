package myapplication.android.pixelpal.ui.all_games.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface AllGamesIntent : MviIntent {

    data object Init: AllGamesIntent

    data object GetGames: AllGamesIntent

    data class GetAllSameSeries(
        val gameId: Long
    ): AllGamesIntent

    data class GetGameParentSeries(
        val gameId: Long
    ): AllGamesIntent

    data class GetAllCurrentReleases(
        val startDate: String,
        val currentDate: String,
    ): AllGamesIntent

    data class GetAllNextReleases(
        val endDate: String,
        val currentDate: String
    ): AllGamesIntent
}