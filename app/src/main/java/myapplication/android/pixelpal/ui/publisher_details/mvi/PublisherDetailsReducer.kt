package myapplication.android.pixelpal.ui.publisher_details.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer
import myapplication.android.pixelpal.ui.publisher_details.model.PublisherDetailsResult

class PublisherDetailsReducer : MviReducer<
        PublisherDetailsPartialState,
        PublisherDetailsState> {
    override fun reduce(
        prevState: PublisherDetailsState,
        partialState: PublisherDetailsPartialState
    ): PublisherDetailsState =
        when(partialState){
            is PublisherDetailsPartialState.DataLoaded -> updateLoaded(prevState, partialState.data)
            is PublisherDetailsPartialState.Error -> updateError(prevState, partialState.throwable)
            PublisherDetailsPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateLoaded(prevState: PublisherDetailsState, ui: PublisherDetailsResult) =
        prevState.copy(ui = LceState.Content(ui), page = prevState.page + 1)

    private fun updateLoading(prevState: PublisherDetailsState) =
        prevState.copy(ui = LceState.Loading)

    private fun updateError(prevState: PublisherDetailsState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))
}