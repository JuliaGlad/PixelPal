package myapplication.android.pixelpal.data.models.stores

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StoresList(
   @SerialName("results") val items: List<Store>
)