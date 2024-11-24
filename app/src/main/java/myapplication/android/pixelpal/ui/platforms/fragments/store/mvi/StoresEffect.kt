package myapplication.android.pixelpal.ui.platforms.fragments.store.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

open class StoresEffect: MviEffect {

    data class OpenStoresDetailsScreen(val storeId: Long): StoresEffect()

}