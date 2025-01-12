package myapplication.android.pixelpal.ui.creator_details.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviStore

class CreatorDetailsStore(
    actor: CreatorDetailsActor,
    reducer: CreatorDetailsReducer
): MviStore<
        CreatorDetailsPartialState,
        CreatorDetailsIntent,
        CreatorDetailsState,
        CreatorDetailsEffect>(reducer, actor) {
    override fun initialStateCreator(): CreatorDetailsState = CreatorDetailsState(LceState.Loading)
}