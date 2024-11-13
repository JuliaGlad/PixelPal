package myapplication.android.pixelpal.data.models.gamesNews

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GamesNewsList(
   @SerialName("results") val items: List<GamesNews>
)