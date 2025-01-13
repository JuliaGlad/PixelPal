package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface PlatformDetailsEffect: MviEffect {

    data object NavigateBack: PlatformDetailsEffect

}