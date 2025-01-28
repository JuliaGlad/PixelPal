package myapplication.android.pixelpal.ui.profile.login.mvi

import myapplication.android.pixelpal.ui.mvi.MviPartialState

sealed interface LoginPartialState: MviPartialState{

    data object Init: LoginPartialState

    data object Loading: LoginPartialState

    data class Error(val throwable: Throwable): LoginPartialState

    data object LoggedIn: LoginPartialState
}