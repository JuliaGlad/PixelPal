package myapplication.android.pixelpal.ui.all_creators.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AllCreatorsStoreFactory(
    private val actor: AllCreatorsActor,
    private val reducer: AllCreatorsReducer
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllCreatorsStore(reducer, actor) as T
    }

}