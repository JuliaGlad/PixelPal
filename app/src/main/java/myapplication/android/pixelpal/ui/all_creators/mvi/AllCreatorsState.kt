package myapplication.android.pixelpal.ui.all_creators.mvi

import myapplication.android.pixelpal.ui.game_details.model.CreatorsGameUiList
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState

data class AllCreatorsState(val ui: LceState<CreatorsGameUiList>, val page: Int = 0): MviState
