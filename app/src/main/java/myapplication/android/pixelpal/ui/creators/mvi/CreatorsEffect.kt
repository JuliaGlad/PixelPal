package myapplication.android.pixelpal.ui.creators.mvi

import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUiList
import myapplication.android.pixelpal.ui.mvi.MviEffect

open class CreatorsEffect: MviEffect {

    data class openCreatorDetailsScreen(val creatorId: Long): CreatorsEffect()

    data class openAllCreatorsScreen(val creators: List<CreatorsUiList>): CreatorsEffect()
}