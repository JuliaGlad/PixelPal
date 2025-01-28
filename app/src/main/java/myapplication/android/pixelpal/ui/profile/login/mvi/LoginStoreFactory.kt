package myapplication.android.pixelpal.ui.profile.login.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginStoreFactory(
    private val reducer: LoginReducer,
    private val actor: LoginActor
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginStore(reducer, actor) as T
    }

}