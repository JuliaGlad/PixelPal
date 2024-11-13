package myapplication.android.pixelpal.data.models.platforms

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PlatformsList(
   @SerialName("results") val items: List<Platform>
)