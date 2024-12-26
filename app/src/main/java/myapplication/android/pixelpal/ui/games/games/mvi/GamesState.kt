package myapplication.android.pixelpal.ui.games.games.mvi

import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUiList
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState

data class GamesState(val ui: LceState<GamesShortDataUiList>, val page: Int = 0): MviState