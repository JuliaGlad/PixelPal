package myapplication.android.pixelpal.ui.profile.main.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface ProfileMainIntent: MviIntent {

    data object Init: ProfileMainIntent

    data object GetUserData: ProfileMainIntent

    data class EditUser(val name: String): ProfileMainIntent

    data class DeleteUser(val password: String): ProfileMainIntent

    data object Logout: ProfileMainIntent
}