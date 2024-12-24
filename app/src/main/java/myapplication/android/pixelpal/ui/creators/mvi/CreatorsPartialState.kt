package myapplication.android.pixelpal.ui.creators.mvi

import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUiList
import myapplication.android.pixelpal.ui.mvi.MviPartialState

sealed interface CreatorsPartialState : MviPartialState {

    data object Init : CreatorsPartialState

    data class Error(val throwable: Throwable) : CreatorsPartialState

    data class DataLoaded(val ui: CreatorsUiList) : CreatorsPartialState

    data object Loading : CreatorsPartialState
}