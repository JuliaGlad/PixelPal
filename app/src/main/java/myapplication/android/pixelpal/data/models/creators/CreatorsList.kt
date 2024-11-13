package myapplication.android.pixelpal.data.models.creators

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreatorsList(
    @SerialName("results") val items: List<myapplication.android.pixelpal.data.models.creators.Creator>
)