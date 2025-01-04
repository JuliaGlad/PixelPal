package myapplication.android.pixelpal.data.models.stores.store_link

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StoreLink(
    @SerialName("store_id") val storeId: Long,
    val url: String
)