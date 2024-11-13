package myapplication.android.pixelpal.data.models.creators

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Creator(
    @SerialName("games_count") val gamesCount: Int,
    val id: Long,
    val name: String,
    val role: String,
    val rating: String,
    val image: String
)