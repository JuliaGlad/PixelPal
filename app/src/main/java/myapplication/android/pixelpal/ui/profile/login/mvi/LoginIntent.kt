package myapplication.android.pixelpal.ui.profile.login.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface LoginIntent: MviIntent {

    data object Init: LoginIntent

    data class SignInWithEmailAndPassword(
        val email: String,
        val password: String
    ): LoginIntent
}