package myapplication.android.pixelpal.ui.profile.favorite_games.mvi

import myapplication.android.pixelpal.ui.home.model.GamesMainInfoListUi
import myapplication.android.pixelpal.ui.mvi.LceState
import myapplication.android.pixelpal.ui.mvi.MviState

data class FavoriteGamesState(val ui: LceState<GamesMainInfoListUi>): MviState