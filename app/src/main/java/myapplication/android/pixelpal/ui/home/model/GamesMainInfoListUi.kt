package myapplication.android.pixelpal.ui.home.model

import myapplication.android.pixelpal.ui.all_games.mvi.AllGameResult

class GamesMainInfoListUi(
   val games: MutableList<GamesUi>
): AllGameResult

class GamesUi(
    val gameId: Long,
    val name: String,
    val playTime: Int,
    val releaseDate: String?,
    val rating: Float?,
    val genre: String,
    val uri: String?
)