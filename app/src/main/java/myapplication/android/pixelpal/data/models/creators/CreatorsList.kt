package myapplication.android.pixelpal.data.models.creators

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import myapplication.android.pixelpal.data.models.creators_roles.Role

@Serializable
class CreatorsList(
    @SerialName("results") val items: List<Creator>
)