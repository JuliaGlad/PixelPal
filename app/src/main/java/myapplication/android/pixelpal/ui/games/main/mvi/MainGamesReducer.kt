package myapplication.android.pixelpal.ui.games.main.mvi

import myapplication.android.pixelpal.ui.games.model.GenresUiList
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer

class MainGamesReducer : MviReducer<
        MainGamesPartialState,
        MainGamesState> {

    override fun reduce(
        prevState: MainGamesState,
        partialState: MainGamesPartialState
    ): MainGamesState =
        when(partialState){
            is MainGamesPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is MainGamesPartialState.Error -> updateError(prevState, partialState.throwable)
            MainGamesPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateDataLoaded(prevState: MainGamesState, ui: GenresUiList) = prevState.copy(ui = LceState.Content(ui))

    private fun updateError(prevState: MainGamesState, throwable: Throwable) = prevState.copy(ui = LceState.Error(throwable))

    private fun updateLoading(prevState: MainGamesState) = prevState.copy(ui = LceState.Loading)

}