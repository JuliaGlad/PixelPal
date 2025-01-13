package myapplication.android.pixelpal.data.models.platforms

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PlatformDetails(
    @SerialName("year_end") val yearEnd: Int?,
    val description: String
)