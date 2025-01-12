package myapplication.android.pixelpal.ui.creator_details.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreatorDetailsStoreFactory(
    private val actor: CreatorDetailsActor,
    private val reducer: CreatorDetailsReducer
): ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreatorDetailsStore(actor, reducer) as T
    }
}