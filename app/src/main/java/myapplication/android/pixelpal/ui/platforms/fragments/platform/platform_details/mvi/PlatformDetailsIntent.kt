package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface PlatformDetailsIntent: MviIntent {

    data object Init: PlatformDetailsIntent

    data class GetPlatformDetails(val id: Int): PlatformDetailsIntent

}