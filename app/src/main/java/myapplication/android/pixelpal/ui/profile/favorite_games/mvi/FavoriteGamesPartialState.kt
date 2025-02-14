package myapplication.android.pixelpal.ui.profile.favorite_games.mvi

import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi
import myapplication.android.pixelpal.ui.mvi.MviPartialState

sealed interface FavoriteGamesPartialState: MviPartialState {

    data object Init: FavoriteGamesPartialState

    data object Loading: FavoriteGamesPartialState

    data class Error(val throwable: Throwable): FavoriteGamesPartialState

    data class DataLoaded(val data: GamesMainInfoListUi): FavoriteGamesPartialState
}