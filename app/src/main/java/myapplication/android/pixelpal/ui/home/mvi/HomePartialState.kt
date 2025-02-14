package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi
import myapplication.android.pixelpal.ui.mvi.MviPartialState

sealed interface HomePartialState: MviPartialState {

    data class Error(val throwable: Throwable): HomePartialState

    data class DataLoaded(val ui: HomeContentResult): HomePartialState

    data class TopReleasesUpdated(val ui: GamesMainInfoListUi): HomePartialState

    data class ReleasesUpdated(val ui: GamesMainInfoListUi): HomePartialState

    data class NextReleasesUpdated(val ui: GamesMainInfoListUi): HomePartialState

    data object Loading: HomePartialState
}