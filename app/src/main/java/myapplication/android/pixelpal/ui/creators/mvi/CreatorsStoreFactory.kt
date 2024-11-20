package myapplication.android.pixelpal.ui.creators.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreatorsStoreFactory(
    private val reducer: CreatorsReducer,
    private val actor: CreatorsActor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreatorsStore(reducer, actor) as T
    }
}