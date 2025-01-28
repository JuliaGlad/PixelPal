package myapplication.android.pixelpal.ui.profile.main.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface ProfileMainEffect: MviEffect {

    data class ShowEditDialog(val name: String): ProfileMainEffect

    data object ShowLogoutDialog: ProfileMainEffect

    data object ShowDeleteDialog: ProfileMainEffect

    data object NavigateToLogin: ProfileMainEffect

    data object OpenFavoritesActivity: ProfileMainEffect
}