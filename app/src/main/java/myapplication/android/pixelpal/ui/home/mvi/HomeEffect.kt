package myapplication.android.pixelpal.ui.home.mvi

import myapplication.android.pixelpal.ui.home.model.GamesNewsUi
import myapplication.android.pixelpal.ui.mvi.MviEffect

open class HomeEffect: MviEffect {

    data class ShowDatesDialog(val date: String): HomeEffect()

    data class OpenGameDetailsScreen(val gameId: Long): HomeEffect()

    data class OpenAllGamesScreen(val games: List<GamesNewsUi>): HomeEffect()
}