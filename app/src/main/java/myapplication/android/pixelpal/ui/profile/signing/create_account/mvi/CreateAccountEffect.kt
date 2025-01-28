package myapplication.android.pixelpal.ui.profile.signing.create_account.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface CreateAccountEffect: MviEffect {

    data object NavigateBack: CreateAccountEffect

    data object ShowSnackBar: CreateAccountEffect

    data object OpenProfile: CreateAccountEffect
}