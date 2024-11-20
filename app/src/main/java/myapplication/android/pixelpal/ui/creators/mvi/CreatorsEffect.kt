package myapplication.android.pixelpal.ui.creators.mvi

import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUi
import myapplication.android.pixelpal.ui.mvi.MviEffect

open class CreatorsEffect: MviEffect {

    data class openPresenterDetailsScreen(val presenterId: Long): CreatorsEffect()

    data class openAllPresentersScreen(val presenters: List<CreatorsUi>): CreatorsEffect()
}