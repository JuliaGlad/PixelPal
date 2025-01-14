package myapplication.android.pixelpal.ui.publisher_details.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviStore

class PublisherDetailsStore(
    reducer: PublisherDetailsReducer,
    actor: PublisherDetailsActor
): MviStore<
        PublisherDetailsPartialState,
        PublisherDetailsIntent,
        PublisherDetailsState,
        PublisherDetailsEffect>(
            reducer, actor
        ) {
    override fun initialStateCreator(): PublisherDetailsState = PublisherDetailsState(LceState.Loading)
}