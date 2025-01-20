package myapplication.android.pixelpal.ui.all_games.mvi

import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.mvi.MviPartialState

sealed interface AllGamesPartialState : MviPartialState {

    data object Init: AllGamesPartialState

    data object Loading: AllGamesPartialState

    data class DataLoaded(val ui: AllGameResult): AllGamesPartialState

    data class Error(val throwable: Throwable): AllGamesPartialState
}
