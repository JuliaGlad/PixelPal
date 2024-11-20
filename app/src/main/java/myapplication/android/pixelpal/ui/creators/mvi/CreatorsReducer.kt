package myapplication.android.pixelpal.ui.creators.mvi

import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUi
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer

class CreatorsReducer: MviReducer<CreatorsPartialState, CreatorsState> {
    override fun reduce(
        prevState: CreatorsState,
        partialState: CreatorsPartialState
    ): CreatorsState =
        when(partialState){
            is CreatorsPartialState.Error -> updateError(prevState, partialState.throwable)
            CreatorsPartialState.Loading -> updateLoading(prevState)
            is CreatorsPartialState.DataLoaded -> updateDataPresentersLoaded(prevState, partialState.ui)
        }

    private fun updateDataPresentersLoaded(prevState: CreatorsState, ui: List<CreatorsUi>): CreatorsState{
        return prevState.copy(ui = LceState.Content(ui))
    }

    private fun updateLoading(prevState: CreatorsState): CreatorsState {
        return prevState.copy(ui = LceState.Loading)
    }

    private fun updateError(prevState: CreatorsState, throwable: Throwable): CreatorsState {
        return prevState.copy(ui = LceState.Error(throwable))
    }


}