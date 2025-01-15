package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model.PlatformDetailsResult
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model.PlatformDetailsUi

data class PlatformDetailsState(val ui: LceState<PlatformDetailsResult>, val page: Int = 0): MviState
