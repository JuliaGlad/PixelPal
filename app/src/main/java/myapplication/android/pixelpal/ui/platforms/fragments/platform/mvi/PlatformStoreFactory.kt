package myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlatformStoreFactory(
    private val actor: PlatformActor,
    private val reducer: PlatformReducer
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlatformStore(actor, reducer) as T
    }

}