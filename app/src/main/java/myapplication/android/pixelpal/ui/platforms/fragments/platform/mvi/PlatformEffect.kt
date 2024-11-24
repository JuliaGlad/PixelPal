package myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

open class PlatformEffect: MviEffect {

    data class OpenPlatformDetails(val id: Int): PlatformEffect()

}