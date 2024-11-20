package myapplication.android.pixelpal.ui.creators.mvi

import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUi
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState

data class CreatorsState(val ui: LceState<List<CreatorsUi>>): MviState
