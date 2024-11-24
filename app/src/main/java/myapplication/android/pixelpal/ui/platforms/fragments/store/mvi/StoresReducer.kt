package myapplication.android.pixelpal.ui.platforms.fragments.store.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer
import myapplication.android.pixelpal.ui.platforms.fragments.store.model.StoresUiList

class StoresReducer : MviReducer<StoresPartialState, StoresState> {
    override fun reduce(prevState: StoresState, partialState: StoresPartialState): StoresState =
        when (partialState) {
            is StoresPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is StoresPartialState.Error -> updateError(prevState, partialState.throwable)
            StoresPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateDataLoaded(prevState: StoresState, ui: StoresUiList) =
        prevState.copy(ui = LceState.Content(ui))

    private fun updateLoading(prevState: StoresState) =
        prevState.copy(ui = LceState.Loading)

    private fun updateError(prevState: StoresState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))
}