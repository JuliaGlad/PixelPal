package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface HomeIntent: MviIntent {

    data object Init: HomeIntent

    data class GetGames(
        val currentDate: String,
        val monthEndDate: String,
        val monthStartDate: String
    ): HomeIntent

    data object GetTop: HomeIntent

    data class GetNextReleases(
        val currentDate: String,
        val monthEndDate: String

    ): HomeIntent

    data class GetReleases(
        val startDate: String,
        val currentDate: String
    ): HomeIntent
}