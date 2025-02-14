package myapplication.android.pixelpal.ui.profile.favorite_games.mvi

import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUiList
import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer

class FavoriteGamesReducer : MviReducer<
        FavoriteGamesPartialState,
        FavoriteGamesState> {
    override fun reduce(
        prevState: FavoriteGamesState,
        partialState: FavoriteGamesPartialState
    ): FavoriteGamesState =
        when (partialState) {
            is FavoriteGamesPartialState.DataLoaded -> updateDataLoaded(
                prevState,
                partialState.data
            )

            is FavoriteGamesPartialState.Error -> updateError(prevState, partialState.throwable)
            FavoriteGamesPartialState.Init -> updateInit()
            FavoriteGamesPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateError(prevState: FavoriteGamesState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))

    private fun updateDataLoaded(prevState: FavoriteGamesState, data: GamesMainInfoListUi) =
        prevState.copy(ui = LceState.Content(data))

    private fun updateInit() = FavoriteGamesState(ui = LceState.Loading)

    private fun updateLoading(prevState: FavoriteGamesState) =
        prevState.copy(ui = LceState.Loading)
}