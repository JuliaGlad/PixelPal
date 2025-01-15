package myapplication.android.pixelpal.ui.creator_details.mvi

import myapplication.android.pixelpal.ui.creator_details.model.CreatorDetailsResultUi
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer

class CreatorDetailsReducer: MviReducer<
        CreatorDetailsPartialState,
        CreatorDetailsState> {
    override fun reduce(
        prevState: CreatorDetailsState,
        partialState: CreatorDetailsPartialState
    ): CreatorDetailsState =
        when(partialState){
            is CreatorDetailsPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is CreatorDetailsPartialState.Error -> updateError(prevState, partialState.throwable)
            CreatorDetailsPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateDataLoaded(prevState: CreatorDetailsState, data: CreatorDetailsResultUi) =
        prevState.copy(ui = LceState.Content(data), page = prevState.page + 1)

    private fun updateError(prevState: CreatorDetailsState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))

    private fun updateLoading(prevState: CreatorDetailsState) =
        prevState.copy(ui = LceState.Loading)

}