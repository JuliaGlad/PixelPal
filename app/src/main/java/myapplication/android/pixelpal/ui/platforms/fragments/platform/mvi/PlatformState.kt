package myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi

import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState
import myapplication.android.pixelpal.ui.platforms.fragments.platform.model.PlatformUiList

data class PlatformState(val ui: LceState<PlatformUiList>): MviState