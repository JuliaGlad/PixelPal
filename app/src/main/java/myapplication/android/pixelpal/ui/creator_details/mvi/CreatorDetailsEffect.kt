package myapplication.android.pixelpal.ui.creator_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface CreatorDetailsEffect: MviEffect {

    data object NavigateBack: CreatorDetailsEffect

}