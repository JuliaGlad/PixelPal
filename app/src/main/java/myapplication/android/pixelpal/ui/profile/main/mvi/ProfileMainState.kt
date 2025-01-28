package myapplication.android.pixelpal.ui.profile.main.mvi

import myapplication.android.pixelpal.ui.mvi.MviState
import myapplication.android.pixelpal.ui.profile.main.model.UserDataUi

data class ProfileMainState(val ui: ProfileMainLceState): MviState

sealed interface ProfileMainLceState {

    data object Loading: ProfileMainLceState

    data class DataLoaded(val content: UserDataUi?): ProfileMainLceState

    data object LoggedOut: ProfileMainLceState

    data class Error(val throwable: Throwable): ProfileMainLceState
}