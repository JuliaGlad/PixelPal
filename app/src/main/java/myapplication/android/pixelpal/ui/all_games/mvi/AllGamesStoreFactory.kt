package myapplication.android.pixelpal.ui.all_games.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AllGamesStoreFactory(
    private val reducer: AllGamesReducer,
    private val actor: AllGamesActor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AllGamesStore(reducer, actor) as T
}