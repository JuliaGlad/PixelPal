package myapplication.android.pixelpal.domain.model.games

data class GamesMainInfoListDomain(
    val items: List<GamesNewsDomain>
)

class GamesNewsDomain(
    val gameId: Long,
    val name: String,
    val playTime: Int,
    val releaseDate: String?,
    val rating: Float?,
    val genre: String,
    val uri: String?
)