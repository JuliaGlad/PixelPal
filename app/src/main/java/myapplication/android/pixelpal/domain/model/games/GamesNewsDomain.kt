package myapplication.android.pixelpal.domain.model.games

data class GamesNewsDomain(
    val gameId: Long,
    val name: String,
    val releaseDate: String?,
    val rating: Float?,
    val genre: String,
    val uri: String?
)