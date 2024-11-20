package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviReducer

class HomeReducer : MviReducer<HomePartialState, HomeState> {

    override fun reduce(prevState: HomeState, partialState: HomePartialState): HomeState =
        when (partialState) {
            is HomePartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is HomePartialState.Error -> updateError(prevState, partialState.throwable)
            HomePartialState.Loading -> updateLoading(prevState)
        }

    private fun updateDataLoaded(prevState: HomeState, ui: HomeContentResult): HomeState{
        return prevState.copy(ui = LceState.Content(ui))
    }

    private fun updateLoading(prevState: HomeState): HomeState{
        return prevState.copy(ui = LceState.Loading)
    }

    private fun updateError(prevState: HomeState, throwable: Throwable): HomeState{
        return prevState.copy(ui = LceState.Error(throwable))
    }

}