package myapplication.android.pixelpal.ui.games.main.mvi

import myapplication.android.pixelpal.ui.games.model.GenreUiDescription
import myapplication.android.pixelpal.ui.games.model.GenresUiList
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState

data class MainGamesState(val ui: LceState<GenresUiList>) : MviState