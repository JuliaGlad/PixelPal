package myapplication.android.pixelpal.ui.games.games.model

import myapplication.android.pixelpal.ui.game_details.model.GameDetailsResult

class GamesShortDataUiList(
    val items: MutableList<GamesShortDataUi>
): GameDetailsResult

class GamesShortDataUi(
    val id: Long,
    val name: String,
    val rating: Int?,
    val releaseDate: String?,
    val playtime: Int,
    val image: String
)