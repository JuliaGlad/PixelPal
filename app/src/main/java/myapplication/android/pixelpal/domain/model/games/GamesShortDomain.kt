package myapplication.android.pixelpal.domain.model.games

class GamesShortDomain(
    val id: Long,
    val name: String,
    val releaseDate: String,
    val genres: List<String>,
    val playtime: Int,
    val image: String
)