package myapplication.android.pixelpal.ui.home.mvi

import android.widget.ViewSwitcher.ViewFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeStoreFactory(
    private val reducer: HomeReducer,
    private val actor: HomeActor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeStore(reducer, actor) as T
    }
}