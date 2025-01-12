package myapplication.android.pixelpal.ui.creator_details.mvi

import myapplication.android.pixelpal.ui.creator_details.model.CreatorDetailsUi
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState

data class CreatorDetailsState(val ui: LceState<CreatorDetailsUi>): MviState
