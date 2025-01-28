package myapplication.android.pixelpal.ui.profile.signing.create_account.mvi

import myapplication.android.pixelpal.ui.mvi.MviPartialState

sealed interface CreateAccountPartialState: MviPartialState {

    data object Init: CreateAccountPartialState

    data object Loading: CreateAccountPartialState

    data object AccountCreated: CreateAccountPartialState

    data class Error(val throwable: Throwable): CreateAccountPartialState
}