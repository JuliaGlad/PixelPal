package myapplication.android.pixelpal.ui.platforms.fragments.store.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviStore

class StoresStore(
    reducer: StoresReducer,
    actor: StoresActor
): MviStore<StoresPartialState, StoresIntent, StoresState, StoresEffect>(
    reducer, actor
) {
    override fun initialStateCreator(): StoresState = StoresState(LceState.Loading)
}