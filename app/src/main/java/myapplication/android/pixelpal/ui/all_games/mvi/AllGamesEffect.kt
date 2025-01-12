package myapplication.android.pixelpal.ui.all_games.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface AllGamesEffect: MviEffect {

    data object NavigateBack: AllGamesEffect

    data class OpenGameDetails(
        val gameId: Long,
        val genres: String,
        val name: String,
        val releaseDate: String,
        val image: String
    ): AllGamesEffect
}