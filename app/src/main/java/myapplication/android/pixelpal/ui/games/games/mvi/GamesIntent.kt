package myapplication.android.pixelpal.ui.games.games.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface GamesIntent: MviIntent {

    data object Init: GamesIntent

    data class GetGames(val id: Long): GamesIntent

}