package myapplication.android.pixelpal.data.models.stores

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Store(
    @SerialName("image_background") val image: String,
    val domain: String?,
    val id: Int,
    val name: String,
    @SerialName("games_count") val projects: Int
)