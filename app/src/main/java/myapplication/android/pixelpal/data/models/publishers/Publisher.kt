package myapplication.android.pixelpal.data.models.publishers

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Publisher(
    @SerialName("games_count") val gamesCount: Int,
    @SerialName("image_background") val image: String?,
    val id: Long,
    val name: String
)