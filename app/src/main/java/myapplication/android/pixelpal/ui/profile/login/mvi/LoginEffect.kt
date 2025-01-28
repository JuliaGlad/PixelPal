package myapplication.android.pixelpal.ui.profile.login.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface LoginEffect: MviEffect {

    data object OpenCreateAccountActivity : LoginEffect

    data object NavigateToProfile : LoginEffect

    data object ShowSnackBar : LoginEffect
}