package myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer
import myapplication.android.pixelpal.ui.platforms.fragments.platform.model.PlatformUiList

class PlatformReducer: MviReducer<PlatformPartialState, PlatformState> {
    override fun reduce(
        prevState: PlatformState,
        partialState: PlatformPartialState
    ): PlatformState =
        when(partialState){
            is PlatformPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is PlatformPartialState.Error -> updateError(prevState, partialState.throwable)
            PlatformPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateDataLoaded(prevState: PlatformState, ui: PlatformUiList) =
        prevState.copy(ui = LceState.Content(ui))

    private fun updateError(prevState: PlatformState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))

    private fun updateLoading(prevState: PlatformState) =
        prevState.copy(ui = LceState.Loading)
}