package myapplication.android.pixelpal.ui.profile.main.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer
import myapplication.android.pixelpal.ui.profile.main.model.UserDataUi

class ProfileMainReducer : MviReducer<
        ProfileMainPartialState,
        ProfileMainState> {
    override fun reduce(
        prevState: ProfileMainState,
        partialState: ProfileMainPartialState
    ): ProfileMainState =
        when (partialState) {
            ProfileMainPartialState.AccountDeleted -> updateLoggedOut(prevState)
            is ProfileMainPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is ProfileMainPartialState.Error -> updateError(prevState, partialState.throwable)
            ProfileMainPartialState.Loading -> updateLoading(prevState)
            ProfileMainPartialState.UserSignedOut -> updateLoggedOut(prevState)
            is ProfileMainPartialState.DataUpdated -> updateDataUpdated(prevState, partialState.name)
        }

    private fun updateDataUpdated(prevState: ProfileMainState, name: String): ProfileMainState{
        val data = (prevState.ui as ProfileMainLceState.DataLoaded).content
        return ProfileMainState(ProfileMainLceState.DataLoaded(
            UserDataUi(
                name,
                data?.email,
                data?.uri
            )
        ))
    }

    private fun updateLoggedOut(prevState: ProfileMainState) =
        prevState.copy(ui = ProfileMainLceState.LoggedOut)

    private fun updateDataLoaded(prevState: ProfileMainState, ui: UserDataUi) =
        prevState.copy(ui = ProfileMainLceState.DataLoaded(ui))

    private fun updateLoading(prevState: ProfileMainState) =
        prevState.copy(ui = ProfileMainLceState.Loading)

    private fun updateError(prevState: ProfileMainState, throwable: Throwable) =
        prevState.copy(ui = ProfileMainLceState.Error(throwable))
}