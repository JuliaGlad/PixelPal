package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.mvi.MviReducer

class HomeReducer : MviReducer<HomePartialState, HomeState> {

    override fun reduce(prevState: HomeState, partialState: HomePartialState): HomeState =
        when (partialState) {
            is HomePartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is HomePartialState.Error -> updateError(prevState, partialState.throwable)
            HomePartialState.Loading -> updateLoading(prevState)
            is HomePartialState.TopReleasesUpdated -> updateTopContent(prevState, partialState.ui)
            is HomePartialState.NextReleasesUpdated -> updateNextReleasesContent(prevState, partialState.ui)
            is HomePartialState.ReleasesUpdated -> updateReleasesContent(prevState, partialState.ui)
        }

    private fun updateReleasesContent(prevState: HomeState, ui: GamesNewsListUi) : HomeState=
        prevState.copy(
            ui = HomeLceState.UpdateReleasesContent(ui),
            newReleasesPage = prevState.newReleasesPage + 1
        )

    private fun updateNextReleasesContent(prevState: HomeState, ui: GamesNewsListUi) : HomeState=
        prevState.copy(
            ui = HomeLceState.UpdateNextReleasesContent(ui),
            nextReleasesPage = prevState.nextReleasesPage + 1
        )

    private fun updateTopContent(prevState: HomeState, ui: GamesNewsListUi) : HomeState=
        prevState.copy(
            ui = HomeLceState.UpdateTopContent(ui),
            topPage = prevState.topPage + 1
        )

    private fun updateDataLoaded(prevState: HomeState, ui: HomeContentResult) =
        prevState.copy(ui = HomeLceState.Content(ui))


    private fun updateLoading(prevState: HomeState) =
        prevState.copy(ui = HomeLceState.Loading)


    private fun updateError(prevState: HomeState, throwable: Throwable) =
        prevState.copy(ui = HomeLceState.Error(throwable))


}