package myapplication.android.pixelpal.ui.all_games.mvi

import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState

data class AllGamesState(val ui: LceState<AllGameResult>, val page: Int = 0) : MviState