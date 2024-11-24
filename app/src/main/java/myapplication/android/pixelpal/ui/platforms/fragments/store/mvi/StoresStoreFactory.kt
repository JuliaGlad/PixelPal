package myapplication.android.pixelpal.ui.platforms.fragments.store.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StoresStoreFactory(
    private val reducer: StoresReducer,
    private val actor: StoresActor
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StoresStore(reducer, actor) as T
    }

}