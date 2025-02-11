package myapplication.android.pixelpal.ui.platforms.fragments.store.mvi

import myapplication.android.pixelpal.ui.mvi.MviPartialState
import myapplication.android.pixelpal.ui.platforms.fragments.store.model.StoresUiList

sealed interface StoresPartialState: MviPartialState {

    data class Error(val throwable: Throwable): StoresPartialState

    data class DataLoaded(val ui: StoresUiList): StoresPartialState

    data object Loading: StoresPartialState

    data object Init: StoresPartialState
}