package myapplication.android.pixelpal.data.models.stores.store

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StoresList(
   @SerialName("results") val items: List<Store>
)