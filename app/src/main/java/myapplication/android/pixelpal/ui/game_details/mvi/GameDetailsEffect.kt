package myapplication.android.pixelpal.ui.game_details.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface GameDetailsEffect: MviEffect {

    data object NavigateBack: GameDetailsEffect

}