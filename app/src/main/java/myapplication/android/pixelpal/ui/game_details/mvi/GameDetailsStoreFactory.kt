package myapplication.android.pixelpal.ui.game_details.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameDetailsStoreFactory(
    private val reducer: GameDetailsReducer,
    private val actor: GameDetailsActor
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameDetailsStore(reducer, actor) as T
    }
}