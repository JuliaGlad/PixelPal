package myapplication.android.pixelpal.ui.games.games.model

class GamesShortDataUiList(
    val items: List<GamesShortDataUi>
)

class GamesShortDataUi(
    val id: Long,
    val name: String,
    val rating: Int?,
    val releaseDate: String,
    val playtime: Int,
    val image: String
)