package myapplication.android.pixelpal.ui.games.main.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface MainGamesIntent: MviIntent {

    data object Init : MainGamesIntent

    data object GetGenres: MainGamesIntent
}