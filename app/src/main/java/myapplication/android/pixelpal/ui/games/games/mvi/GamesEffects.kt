package myapplication.android.pixelpal.ui.games.games.mvi

import myapplication.android.pixelpal.ui.mvi.MviEffect

class GamesEffects: MviEffect {

    data class OpenGameDetails(val gameId: Long): MviEffect

    data object OpenFilters: MviEffect
}