package myapplication.android.pixelpal.ui.games.main.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainGamesStoreFactory(
    private val reducer: MainGamesReducer,
    private val actor: MainGamesActor
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainGamesStore(actor, reducer) as T
    }

}