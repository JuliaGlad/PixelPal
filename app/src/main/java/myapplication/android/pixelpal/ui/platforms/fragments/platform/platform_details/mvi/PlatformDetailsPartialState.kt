package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviPartialState
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model.PlatformDetailsUi

sealed interface PlatformDetailsPartialState: MviPartialState{

    data object Loading: PlatformDetailsPartialState

    data class DataLoaded(val ui: PlatformDetailsUi): PlatformDetailsPartialState

    data class Error(val throwable: Throwable): PlatformDetailsPartialState
}