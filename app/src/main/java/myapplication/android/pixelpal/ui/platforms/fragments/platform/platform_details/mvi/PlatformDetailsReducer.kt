package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model.PlatformDetailsUi
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model.StoreDetailsUi
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi.StoreDetailsState

class PlatformDetailsReducer: MviReducer<
        PlatformDetailsPartialState,
        PlatformDetailsState> {
    override fun reduce(
        prevState: PlatformDetailsState,
        partialState: PlatformDetailsPartialState
    ): PlatformDetailsState =
        when(partialState){
            is PlatformDetailsPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is PlatformDetailsPartialState.Error -> updateError(prevState, partialState.throwable)
            PlatformDetailsPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateDataLoaded(prevState: PlatformDetailsState, ui: PlatformDetailsUi) =
        prevState.copy(ui = LceState.Content(ui))

    private fun updateLoading(prevState: PlatformDetailsState) =
        prevState.copy(ui = LceState.Loading)

    private fun updateError(prevState: PlatformDetailsState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))
}