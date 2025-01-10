package myapplication.android.pixelpal.ui.all_games.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

sealed interface AllGamesEffect: MviEffect {

    data object NavigateBack: AllGamesEffect

}