package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlatformDetailsStoreFactory(
    private val reducer: PlatformDetailsReducer,
    private val actor: PlatformDetailsActor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlatformDetailsStore(reducer, actor) as T
    }

}