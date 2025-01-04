package myapplication.android.pixelpal.ui.game_details.mvi

import myapplication.android.pixelpal.ui.game_details.model.CreatorsGameUiList
import myapplication.android.pixelpal.ui.game_details.model.StoresSellingGameUiList
import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUiList
import myapplication.android.pixelpal.ui.mvi.MviReducer

class GameDetailsReducer : MviReducer<GameDetailsPartialState, GameDetailsState> {
    override fun reduce(
        prevState: GameDetailsState,
        partialState: GameDetailsPartialState
    ): GameDetailsState =
        when (partialState) {
            is GameDetailsPartialState.UpdateCreators -> updateCreators(
                prevState,
                partialState.creatorsGameUiList
            )

            is GameDetailsPartialState.UpdateDataLoaded -> updateDataLoaded(
                prevState,
                partialState.ui
            )

            is GameDetailsPartialState.UpdateError -> updateError(prevState, partialState.throwable)
            GameDetailsPartialState.UpdateLoading -> updateLoading(prevState)
            is GameDetailsPartialState.UpdateParentGamesAndAddition ->
                updateParentGamesAndAddition(
                    prevState,
                    partialState.parenGames,
                    partialState.additions
                )

            is GameDetailsPartialState.UpdateSameSeries -> updateSameSeries(
                prevState,
                partialState.sameSeries
            )

            is GameDetailsPartialState.UpdateStores -> updateStores(prevState, partialState.stores)
        }

    private fun updateStores(
        prevState: GameDetailsState,
        stores: StoresSellingGameUiList
    ) =
        prevState.copy(
            ui = GameDetailsLceState.UpdateStoresSellingGame(stores),
            storesPage = prevState.storesPage + 1
        )

    private fun updateParentGamesAndAddition(
        prevState: GameDetailsState,
        parenGames: GamesShortDataUiList,
        addition: GamesShortDataUiList
    ): GameDetailsState {
        val ui = GamesShortDataUiList(parenGames.items)
        ui.items.addAll(addition.items)
        return prevState.copy(
            ui = GameDetailsLceState.UpdateParentGamesAndAdditions(ui),
            parentsAndAdditionPage = prevState.parentsAndAdditionPage + 1
        )
    }

    private fun updateSameSeries(prevState: GameDetailsState, ui: GamesShortDataUiList) =
        prevState.copy(
            ui = GameDetailsLceState.UpdateSameSeries(ui),
            seriesPage = prevState.seriesPage + 1
        )

    private fun updateCreators(prevState: GameDetailsState, ui: CreatorsGameUiList) =
        prevState.copy(
            ui = GameDetailsLceState.UpdateCreators(ui),
            creatorsPage = prevState.creatorsPage + 1
        )

    private fun updateDataLoaded(prevState: GameDetailsState, ui: GameDetailsContentResult) =
        prevState.copy(ui = GameDetailsLceState.Content(ui))

    private fun updateError(prevState: GameDetailsState, throwable: Throwable) =
        prevState.copy(ui = GameDetailsLceState.Error(throwable))

    private fun updateLoading(prevState: GameDetailsState) =
        prevState.copy(ui = GameDetailsLceState.Loading)
}