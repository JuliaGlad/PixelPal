package myapplication.android.pixelpal.data.models.gamesMain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GamesShortDataList (
    @SerialName("results") val items: List<GameShortData>
)