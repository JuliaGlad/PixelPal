package myapplication.android.pixelpal.ui.profile.signing.create_account.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateAccountStoreFactory(
    private val reducer: CreateAccountReducer,
    private val accountActor: CreateAccountActor
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateAccountStore(reducer, accountActor) as T
    }
}