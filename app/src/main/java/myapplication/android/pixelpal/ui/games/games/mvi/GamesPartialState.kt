package myapplication.android.pixelpal.ui.games.games.mvi

import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUiList
import myapplication.android.pixelpal.ui.mvi.MviPartialState

sealed interface GamesPartialState: MviPartialState {

    data object Loading: GamesPartialState

    data class DataLoaded(val ui: GamesShortDataUiList): GamesPartialState

    data class Error(val throwable: Throwable): GamesPartialState
}