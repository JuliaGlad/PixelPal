package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviStore

class StoreDetailsStore(
    reducer: StoreDetailsReducer,
    actor: StoreDetailsActor
): MviStore<
        StoreDetailsPartialState,
        StoreDetailsIntent,
        StoreDetailsState,
        StoreDetailsEffect>(reducer, actor) {
    override fun initialStateCreator(): StoreDetailsState = StoreDetailsState(LceState.Loading)
}