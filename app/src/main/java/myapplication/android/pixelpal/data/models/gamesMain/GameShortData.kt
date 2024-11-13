package myapplication.android.pixelpal.data.models.gamesMain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GameShortData(
    @SerialName("released") val releaseDate: String,
    @SerialName("background_image") val image: String,
    val playtime: Int,
    val genres: List<String>,
    val id: Long,
    val name: String
)