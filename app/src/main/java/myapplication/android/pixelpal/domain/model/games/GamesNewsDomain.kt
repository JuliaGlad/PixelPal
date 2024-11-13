package myapplication.android.pixelpal.domain.model.games

class GamesNewsDomain(
    val gameId: Long,
    val name: String,
    val releaseDate: String,
    val genres: List<String>,
    val uri: String
)