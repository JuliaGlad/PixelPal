package myapplication.android.pixelpal.data.models.gamesNews

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import myapplication.android.pixelpal.data.models.GameRating

@Serializable
class GamesMainInfo(
    @SerialName("released") val releaseDate: String?,
    @SerialName("background_image") val image: String?,
    @SerialName("metacritic") val rating: Float?,
    @SerialName("esrb_rating") val ageRating: GameRating?,
    @SerialName("playtime")val playTime: Int,
    val id: Long,
    val name: String,
    val genres: JsonArray? = null,
    val genreName: String? = null
)