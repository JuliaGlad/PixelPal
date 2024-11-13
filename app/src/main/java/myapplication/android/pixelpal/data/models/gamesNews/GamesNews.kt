package myapplication.android.pixelpal.data.models.gamesNews

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GamesNews(
    @SerialName("released") val releaseDate: String,
    @SerialName("background_image") val image: String,
    val genres: List<String>,
    val id: Long,
    val name: String
)