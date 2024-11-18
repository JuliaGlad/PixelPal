package myapplication.android.pixelpal.data.models.gamesNews

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray

@Serializable
class GamesNews(
    @SerialName("released") val releaseDate: String?,
    @SerialName("background_image") val image: String?,
    @SerialName("metacritic") val rating: Float?,
    @SerialName("esrb_rating") val ageRating: GameRating?,
    val genres: JsonArray,
    val id: Long,
    val name: String
)