package myapplication.android.pixelpal.ui.profile.favorite_games.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoriteGamesStoreFactory(
    private val actor: FavoriteGamesActor,
    private val reducer: FavoriteGamesReducer
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteGamesStore(actor, reducer) as T
    }
}