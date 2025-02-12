package myapplication.android.pixelpal.ui.games.games.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface GamesEffects : MviEffect {
    data class OpenGameDetails(
        val gameId: Long,
        val name: String,
        val releaseDate: String,
        val image: String
    ) : GamesEffects
}