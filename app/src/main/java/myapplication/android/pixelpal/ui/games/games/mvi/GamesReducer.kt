package myapplication.android.pixelpal.ui.games.games.mvi

import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUiList
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer

class GamesReducer: MviReducer<
        GamesPartialState,
        GamesState> {
    override fun reduce(prevState: GamesState, partialState: GamesPartialState): GamesState =
        when(partialState){
            is GamesPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is GamesPartialState.Error -> updateError(prevState, partialState.throwable)
            GamesPartialState.Loading -> updateLoading(prevState)
            GamesPartialState.Init -> updateInit()
        }

    private fun updateInit() = GamesState(LceState.Loading, page = 0)

    private fun updateDataLoaded(prevState: GamesState, ui: GamesShortDataUiList) =
        prevState.copy(ui = LceState.Content(ui), page = prevState.page + 1)

    private fun updateError(prevState: GamesState, throwable: Throwable) = prevState.copy(ui = LceState.Error(throwable))

    private fun updateLoading(prevState: GamesState) = prevState.copy(ui = LceState.Loading)
}