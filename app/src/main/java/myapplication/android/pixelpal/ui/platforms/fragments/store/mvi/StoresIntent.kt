package myapplication.android.pixelpal.ui.platforms.fragments.store.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface StoresIntent: MviIntent {

    data object Init: StoresIntent

    data object GetStores: StoresIntent

    data class GetStoresByQuery(val query: String): StoresIntent
}