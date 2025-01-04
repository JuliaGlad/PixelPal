package myapplication.android.pixelpal.domain.model.games

class GamesShortDomainList(
    val games: List<GameShortDomain>
)

class GameShortDomain(
    val id: Long,
    val name: String,
    val rating: Int?,
    val releaseDate: String?,
    val playtime: Int,
    val image: String
)