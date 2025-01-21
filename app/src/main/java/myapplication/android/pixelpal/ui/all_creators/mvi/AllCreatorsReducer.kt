package myapplication.android.pixelpal.ui.all_creators.mvi

import myapplication.android.pixelpal.ui.game_details.model.CreatorsGameUiList
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer

class AllCreatorsReducer: MviReducer<
        AllCreatorsPartialState, AllCreatorsState> {
    override fun reduce(
        prevState: AllCreatorsState,
        partialState: AllCreatorsPartialState
    ): AllCreatorsState =
        when(partialState){
            is AllCreatorsPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is AllCreatorsPartialState.Error -> updateError(prevState, partialState.throwable)
            AllCreatorsPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateLoading(prevState: AllCreatorsState) = prevState.copy(ui = LceState.Loading)

    private fun updateError(prevState: AllCreatorsState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))

    private fun updateDataLoaded(prevState: AllCreatorsState, ui: CreatorsGameUiList) =
        prevState.copy(ui = LceState.Content(ui), page = prevState.page + 1)

}