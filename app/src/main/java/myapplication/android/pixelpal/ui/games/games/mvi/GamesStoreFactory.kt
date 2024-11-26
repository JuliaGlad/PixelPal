package myapplication.android.pixelpal.ui.games.games.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GamesStoreFactory(
    private val reducer: GamesReducer,
    private val actor: GamesActor
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        GamesStore(reducer, actor) as T
}