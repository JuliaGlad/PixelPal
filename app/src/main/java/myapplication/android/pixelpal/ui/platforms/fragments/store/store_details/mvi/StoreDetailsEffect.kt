package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface StoreDetailsEffect: MviEffect {

    data object NavigateBack: StoreDetailsEffect

}