package myapplication.android.pixelpal.ui.publisher_details.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PublisherDetailsStoreFactory(
    private val actor: PublisherDetailsActor,
    private val reducer: PublisherDetailsReducer
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PublisherDetailsStore(reducer, actor) as T
    }

}