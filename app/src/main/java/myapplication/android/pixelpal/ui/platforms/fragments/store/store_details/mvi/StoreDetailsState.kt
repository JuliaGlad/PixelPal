package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model.StoreDetailsResult
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model.StoreDetailsUi

data class StoreDetailsState(val ui: LceState<StoreDetailsResult>, val page: Int = 0): MviState