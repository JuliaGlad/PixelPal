package myapplication.android.pixelpal.ui.profile.favorite_games.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface FavoriteGamesEffect: MviEffect {

    data class OpenGameDetails(
        val gameId: Long,
        val name: String,
        val genres: String,
        val released: String?,
        val image: String?
    ): FavoriteGamesEffect

    data object NavigateBack: FavoriteGamesEffect
}