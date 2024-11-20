package myapplication.android.pixelpal.ui.creators.mvi

import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUi
import myapplication.android.pixelpal.ui.mvi.MviPartialState

sealed interface CreatorsPartialState: MviPartialState {
    data class Error(val throwable: Throwable): CreatorsPartialState

    data class DataLoaded(val ui: List<CreatorsUi>): CreatorsPartialState

    data object Loading: CreatorsPartialState
}