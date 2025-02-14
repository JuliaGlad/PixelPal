package myapplication.android.pixelpal.ui.profile.favorite_games.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface FavoriteGamesIntent: MviIntent {

    data object Init: FavoriteGamesIntent

    data object GetGames: FavoriteGamesIntent
}