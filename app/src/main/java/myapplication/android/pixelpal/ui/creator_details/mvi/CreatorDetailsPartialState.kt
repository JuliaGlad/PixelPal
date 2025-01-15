package myapplication.android.pixelpal.ui.creator_details.mvi

import myapplication.android.pixelpal.ui.creator_details.model.CreatorDetailsResultUi
import myapplication.android.pixelpal.ui.creator_details.model.CreatorDetailsUi
import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUiList
import myapplication.android.pixelpal.ui.mvi.MviPartialState

sealed interface CreatorDetailsPartialState: MviPartialState {

    data object Loading: CreatorDetailsPartialState

    data class DataLoaded(val ui: CreatorDetailsResultUi): CreatorDetailsPartialState

    data object GamesLoaded: CreatorDetailsPartialState

    data class Error(val throwable: Throwable): CreatorDetailsPartialState
}