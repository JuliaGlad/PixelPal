package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model.StoreDetailsResult
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model.StoreDetailsUi

class StoreDetailsReducer: MviReducer<
        StoreDetailsPartialState,
        StoreDetailsState> {
    override fun reduce(
        prevState: StoreDetailsState,
        partialState: StoreDetailsPartialState
    ): StoreDetailsState =
        when(partialState){
            is StoreDetailsPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.data)
            is StoreDetailsPartialState.Error -> updateError(prevState, partialState.throwable)
            StoreDetailsPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateDataLoaded(prevState: StoreDetailsState, ui: StoreDetailsResult) =
        prevState.copy(ui = LceState.Content(ui), page = prevState.page + 1)

    private fun updateLoading(prevState: StoreDetailsState) =
        prevState.copy(ui = LceState.Loading)

    private fun updateError(prevState: StoreDetailsState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))

}