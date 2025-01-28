package myapplication.android.pixelpal.ui.profile.login.mvi

import myapplication.android.pixelpal.ui.mvi.MviState

data class LoginState(val ui: LoginLceState): MviState

sealed interface LoginLceState{

    data object Init: LoginLceState

    data object Loading: LoginLceState

    data object LoggedIn: LoginLceState

    data class Error(val throwable: Throwable): LoginLceState
}