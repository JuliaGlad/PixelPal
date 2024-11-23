package myapplication.android.pixelpal.domain.model.games

data class GamesNewsListDomain(
    val items: List<GamesNewsDomain>
)

class GamesNewsDomain(
    val gameId: Long,
    val name: String,
    val releaseDate: String?,
    val rating: Float?,
    val genre: String,
    val uri: String?
)