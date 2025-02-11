package myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi

import myapplication.android.pixelpal.ui.mvi.MviPartialState
import myapplication.android.pixelpal.ui.platforms.fragments.platform.model.PlatformUiList

sealed interface PlatformPartialState: MviPartialState {

    data object Loading: PlatformPartialState

    data class DataLoaded(val ui: PlatformUiList): PlatformPartialState

    data object Init: PlatformPartialState

    data class Error(val throwable: Throwable): PlatformPartialState
}