package myapplication.android.pixelpal.data.models.gamesMain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import myapplication.android.pixelpal.data.models.GameRating

@Serializable
class GameShortData(
    @SerialName("released") val releaseDate: String?,
    @SerialName("background_image") val image: String,
    @SerialName("esrb_rating") val ageRating: GameRating?,
    @SerialName("metacritic") val rating: Int?,
    val playtime: Int,
    val id: Long,
    val name: String
)