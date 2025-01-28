package myapplication.android.pixelpal.ui.profile.main.mvi

import myapplication.android.pixelpal.ui.mvi.MviStore

class ProfileMainStore(
    reducer: ProfileMainReducer,
    actor: ProfileMainActor
) : MviStore<
        ProfileMainPartialState,
        ProfileMainIntent,
        ProfileMainState,
        ProfileMainEffect>(
    reducer, actor
) {
    override fun initialStateCreator(): ProfileMainState = ProfileMainState(ProfileMainLceState.Loading)
}