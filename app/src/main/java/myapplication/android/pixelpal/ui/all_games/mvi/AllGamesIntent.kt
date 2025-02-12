package myapplication.android.pixelpal.ui.all_games.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface AllGamesIntent : MviIntent {

    data object Init: AllGamesIntent

    data object GetGames: AllGamesIntent

    data class GetGamesByPlatform(
        val platformId: Long
    ): AllGamesIntent

    data class GetGamesByStore(
        val storeId: Int
    ): AllGamesIntent

    data class GetCreatorGames(
        val creatorId: Long
    ): AllGamesIntent

    data class GetAllSameSeries(
        val gameId: Long
    ): AllGamesIntent

    data class GetGameParentSeries(
        val gameId: Long
    ): AllGamesIntent

    data class GetGamesByPublisher(
        val publisherId: Long
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