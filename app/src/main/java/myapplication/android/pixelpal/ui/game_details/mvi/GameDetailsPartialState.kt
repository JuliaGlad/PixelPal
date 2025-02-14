package myapplication.android.pixelpal.ui.game_details.mvi

import myapplication.android.pixelpal.ui.game_details.model.CreatorsGameUiList
import myapplication.android.pixelpal.ui.game_details.model.StoresSellingGameUiList
import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUiList
import myapplication.android.pixelpal.ui.home.mvi.HomeContentResult
import myapplication.android.pixelpal.ui.mvi.MviPartialState
import myapplication.android.pixelpal.ui.profile.favorite_games.mvi.FavoriteGamesPartialState

sealed interface GameDetailsPartialState : MviPartialState {

    data object UpdateLoading: GameDetailsPartialState

    data class UpdateDataLoaded(val ui: GameDetailsContentResult): GameDetailsPartialState

    data class UpdateError(val throwable: Throwable): GameDetailsPartialState

    data class UpdateStores(val stores: StoresSellingGameUiList): GameDetailsPartialState

    data class UpdateParentGamesAndAddition(
        val parenGames: GamesShortDataUiList,
        val additions: GamesShortDataUiList
    ): GameDetailsPartialState

    data class UpdateSameSeries(
        val sameSeries: GamesShortDataUiList
    ): GameDetailsPartialState

    data class UpdateCreators(val creatorsGameUiList: CreatorsGameUiList): GameDetailsPartialState

    data object GameRemoved : GameDetailsPartialState

    data object GameAdded : GameDetailsPartialState
}