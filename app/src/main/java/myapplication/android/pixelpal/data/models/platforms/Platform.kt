package myapplication.android.pixelpal.data.models.platforms

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Platform(
    @SerialName("image_background") val image: String,
    @SerialName("games_count") val gamesCount: Int,
    @SerialName("year_start") val startYear: Int?,
    val id: Long,
    val name: String
)