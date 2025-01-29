package myapplication.android.pixelpal.data.repository.dto.game

class GamesShortDtoList(
    val games: List<GameShortDto>
)

class GameShortDto(
    val id: Long,
    val name: String,
    val rating: Int?,
    val releaseDate: String?,
    val ageRating: GameRatingDto,
    val playtime: Int,
    val image: String?
)