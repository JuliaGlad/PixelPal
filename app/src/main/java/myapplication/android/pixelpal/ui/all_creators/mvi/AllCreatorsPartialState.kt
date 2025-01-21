package myapplication.android.pixelpal.ui.all_creators.mvi

import myapplication.android.pixelpal.ui.game_details.model.CreatorsGameUiList
import myapplication.android.pixelpal.ui.mvi.MviPartialState

sealed interface AllCreatorsPartialState: MviPartialState {

    data object Loading: AllCreatorsPartialState

    data class DataLoaded(val ui: CreatorsGameUiList): AllCreatorsPartialState

    data class Error(val throwable: Throwable): AllCreatorsPartialState

}