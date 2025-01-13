package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviStore

class PlatformDetailsStore(
    reducer: PlatformDetailsReducer,
    actor: PlatformDetailsActor
): MviStore<
        PlatformDetailsPartialState,
        PlatformDetailsIntent,
        PlatformDetailsState,
        PlatformDetailsEffect>(reducer, actor) {
    override fun initialStateCreator(): PlatformDetailsState = PlatformDetailsState(LceState.Loading)
}