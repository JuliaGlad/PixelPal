package myapplication.android.pixelpal.ui.profile.signing.create_account.mvi

import myapplication.android.pixelpal.ui.mvi.MviState

data class CreateAccountState(val ui: CreateAccountLceState) : MviState

sealed interface CreateAccountLceState{

    data object Init: CreateAccountLceState

    data object Loading: CreateAccountLceState

    data object AccountCreated : CreateAccountLceState

    data class Error(val throwable: Throwable): CreateAccountLceState
}