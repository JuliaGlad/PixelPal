package myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface PlatformIntent: MviIntent {

    data object Init: PlatformIntent

    data object GetPlatforms: PlatformIntent
}