package myapplication.android.pixelpal.ui.all_creators.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface AllCreatorsEffect : MviEffect {

    data object NavigateBack : AllCreatorsEffect

    data class OpenAllCreatorDetails(
        val creatorId: Long,
        val name: String,
        val role: Array<String?>,
        val famousProjects: Int,
        val image: String?
    ) : AllCreatorsEffect
}