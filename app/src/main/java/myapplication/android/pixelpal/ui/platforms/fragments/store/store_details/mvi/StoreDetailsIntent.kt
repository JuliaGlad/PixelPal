package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface StoreDetailsIntent: MviIntent {

    data object Init: StoreDetailsIntent

    data class GetStoreDetails(val id: Int): StoreDetailsIntent

}