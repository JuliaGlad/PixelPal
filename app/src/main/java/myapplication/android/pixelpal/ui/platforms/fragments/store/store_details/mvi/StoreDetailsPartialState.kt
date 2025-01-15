package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviPartialState
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model.StoreDetailsResult
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model.StoreDetailsUi

sealed interface StoreDetailsPartialState: MviPartialState {

    data object Loading: StoreDetailsPartialState

    data class DataLoaded(val data: StoreDetailsResult): StoreDetailsPartialState

    data class Error(val throwable: Throwable): StoreDetailsPartialState
}