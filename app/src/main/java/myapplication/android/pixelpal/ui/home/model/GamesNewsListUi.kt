package myapplication.android.pixelpal.ui.home.model

class GamesNewsListUi(
   val games: List<GamesUi>
)

class GamesUi(
    val gameId: Long,
    val name: String,
    val releaseDate: String?,
    val rating: Float?,
    val genre: String,
    val uri: String?
)