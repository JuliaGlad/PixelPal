package myapplication.android.pixelpal.ui.profile.main.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import myapplication.android.pixelpal.ui.mvi.MviActor

class ProfileMainStoreFactory(
    private val reducer: ProfileMainReducer,
    private val actor: ProfileMainActor
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileMainStore(reducer, actor) as T
    }

}