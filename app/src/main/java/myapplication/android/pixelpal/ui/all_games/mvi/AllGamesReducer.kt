package myapplication.android.pixelpal.ui.all_games.mvi

import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer

class AllGamesReducer: MviReducer<
        AllGamesPartialState,
        AllGamesState> {
    override fun reduce(
        prevState: AllGamesState,
        partialState: AllGamesPartialState
    ): AllGamesState =
        when(partialState){
            is AllGamesPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is AllGamesPartialState.Error -> updateError(prevState, partialState.throwable)
            AllGamesPartialState.Loading -> updateLoading(prevState)
            AllGamesPartialState.Init -> init()
        }

    private fun init() = AllGamesState(LceState.Loading)

    private fun updateError(prevState: AllGamesState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))

    private fun updateLoading(prevState: AllGamesState) =
        prevState.copy(ui = LceState.Loading)

    private fun updateDataLoaded(prevState: AllGamesState, ui: GamesNewsListUi) =
        prevState.copy(ui = LceState.Content(ui), page = prevState.page +  1)

}