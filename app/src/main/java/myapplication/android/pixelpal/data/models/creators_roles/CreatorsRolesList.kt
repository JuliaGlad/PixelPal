package myapplication.android.pixelpal.data.models.creators_roles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreatorsRolesList (
    @SerialName("results") val items: List<Role>
)