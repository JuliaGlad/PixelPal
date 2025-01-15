package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect
import myapplication.android.pixelpal.ui.publisher_details.mvi.PublisherDetailsEffect

sealed interface PlatformDetailsEffect: MviEffect {

    data object NavigateBack: PlatformDetailsEffect

    data class OpenGameDetails(
        val gameId: Long,
        val genres: String,
        val name: String,
        val releaseDate: String,
        val image: String
    ): PlatformDetailsEffect
}