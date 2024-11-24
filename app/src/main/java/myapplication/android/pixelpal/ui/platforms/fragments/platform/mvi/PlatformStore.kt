package myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviStore

class PlatformStore(
    actor: PlatformActor,
    reducer: PlatformReducer
) : MviStore<
        PlatformPartialState,
        PlatformIntent,
        PlatformState,
        PlatformEffect>(
    reducer, actor
) {
    override fun initialStateCreator(): PlatformState = PlatformState(ui = LceState.Loading)
}