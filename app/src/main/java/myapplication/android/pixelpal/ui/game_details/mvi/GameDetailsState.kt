package myapplication.android.pixelpal.ui.game_details.mvi

import myapplication.android.pixelpal.ui.game_details.model.GameDetailsResult
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState

data class GameDetailsState(
    val ui: GameDetailsLceState<GameDetailsResult>,
    val creatorsPage: Int = 0,
    val parentsAndAdditionPage: Int = 0,
    val seriesPage: Int = 0,
    val storesPage: Int = 0
): MviState

sealed interface GameDetailsLceState<out T>{

    data object Loading: GameDetailsLceState<Nothing>

    data class Content<out T>(val data: T): GameDetailsLceState<T>

    data class UpdateStoresSellingGame<out T>(val data: T): GameDetailsLceState<T>

    data class UpdateParentGamesAndAdditions<out T>(val data: T): GameDetailsLceState<T>

    data class UpdateSameSeries<out T>(val data: T): GameDetailsLceState<T>

    data class UpdateCreators<out T>(val data: T): GameDetailsLceState<T>

    data class Error(val throwable: Throwable): GameDetailsLceState<Nothing>

    data object GameRemovedFromFavorites: GameDetailsLceState<Nothing>

    data object GameAddedToFavorites: GameDetailsLceState<Nothing>
}