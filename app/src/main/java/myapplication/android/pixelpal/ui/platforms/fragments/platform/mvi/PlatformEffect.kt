package myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface PlatformEffect : MviEffect {

    data class OpenPlatformDetails(
        val id: Int,
        val name: String,
        val gamesCount: Int,
        val startYear: Int?,
        val background: String
    ) : PlatformEffect

}