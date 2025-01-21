package myapplication.android.pixelpal.ui.all_creators.mvi

import myapplication.android.pixelpal.ui.all_games.mvi.AllGamesIntent
import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface AllCreatorsIntent: MviIntent {

    data object Init: AllCreatorsIntent

    data class GetCreators(
        val gameId: Long
    ): AllCreatorsIntent

}