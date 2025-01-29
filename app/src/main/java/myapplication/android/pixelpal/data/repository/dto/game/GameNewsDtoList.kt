package myapplication.android.pixelpal.data.repository.dto.game

class GameNewsDtoList(
    val items: List<GameNewsDto>
)

class GameNewsDto(
    val gameId: Long,
    val name: String,
    val playTime: Int,
    val releaseDate: String?,
    val rating: Float?,
    val ageRating: GameRatingDto,
    val genre: String,
    val uri: String?
)

class GameRatingDto(
    val name: String?
)