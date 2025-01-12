package myapplication.android.pixelpal.ui.creators.mvi

import myapplication.android.pixelpal.ui.creators.model.creatores.CreatorsUiList
import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface CreatorsEffect: MviEffect {

    data class OpenCreatorDetailsScreen(
        val creatorId: Long,
        val name: String,
        val role: Array<String?>,
        val famousProjects: Int,
        val image: String?
    ): CreatorsEffect

    data class OpenAllCreatorsScreen(val creators: List<CreatorsUiList>): CreatorsEffect
}