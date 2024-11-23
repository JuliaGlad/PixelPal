package myapplication.android.pixelpal.data.models.creators

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import myapplication.android.pixelpal.data.models.creators_roles.Role

@Serializable
class Creator(
    @SerialName("games_count") val gamesCount: Int,
    val id: Long,
    val name: String,
    @SerialName("positions") val role: List<Role>,
    val image: String
)
//"positions":[{"id":2,"name":"director","slug":"director"},{"id":5,"name":"producer","slug":"producer"},{"id":7,"name":"programmer","slug":"programmer"}]