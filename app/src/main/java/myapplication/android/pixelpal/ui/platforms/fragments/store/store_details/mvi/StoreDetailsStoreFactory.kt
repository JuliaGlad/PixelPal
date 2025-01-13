package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StoreDetailsStoreFactory(
    private val reducer: StoreDetailsReducer,
    private val actor: StoreDetailsActor
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StoreDetailsStore(reducer, actor) as T
    }

}