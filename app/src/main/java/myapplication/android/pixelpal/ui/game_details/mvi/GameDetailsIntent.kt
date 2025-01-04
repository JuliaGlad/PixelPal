package myapplication.android.pixelpal.ui.game_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface GameDetailsIntent: MviIntent {

    data object Init : GameDetailsIntent

    data class GetStoresSellingGame(
        val gameId: String
    ): GameDetailsIntent

    data class GetGameMainData(
        val gameId: String
    ): GameDetailsIntent

    data class GetCreators(
        val gameId: String
    ): GameDetailsIntent

    data class GetParentGamesAndAdditions(
        val gameId: String
    ): GameDetailsIntent

    data class GetSameSeries(
        val gameId: String
    ): GameDetailsIntent

}