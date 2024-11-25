package myapplication.android.pixelpal.ui.games.main.mvi

import myapplication.android.pixelpal.ui.games.model.GenresUiList
import myapplication.android.pixelpal.ui.mvi.MviPartialState

sealed interface MainGamesPartialState: MviPartialState {

    data object Loading: MainGamesPartialState

    data class DataLoaded(val ui: GenresUiList): MainGamesPartialState

    data class Error(val throwable: Throwable): MainGamesPartialState
}