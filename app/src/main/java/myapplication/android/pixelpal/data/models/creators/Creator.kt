package myapplication.android.pixelpal.data.models.creators

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Creator(
    @SerialName("games_count") val gamesCount: Int,
    val id: Long,
    val name: String,
//    val role: String, //TODO("DELETE ROLE FIELD")
//    val rating: String,
    val image: String
)
//"positions":[{"id":2,"name":"director","slug":"director"},{"id":5,"name":"producer","slug":"producer"},{"id":7,"name":"programmer","slug":"programmer"}]