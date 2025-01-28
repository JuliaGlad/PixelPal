package myapplication.android.pixelpal.ui.profile.main.mvi

import myapplication.android.pixelpal.ui.mvi.MviPartialState
import myapplication.android.pixelpal.ui.profile.main.model.UserDataUi

sealed interface ProfileMainPartialState : MviPartialState {

    data object Loading : ProfileMainPartialState

    data class Error(val throwable: Throwable) : ProfileMainPartialState

    data class DataLoaded(val ui: UserDataUi): ProfileMainPartialState

    data object AccountDeleted : ProfileMainPartialState

    data class DataUpdated(val name: String): ProfileMainPartialState

    data object UserSignedOut: ProfileMainPartialState
}