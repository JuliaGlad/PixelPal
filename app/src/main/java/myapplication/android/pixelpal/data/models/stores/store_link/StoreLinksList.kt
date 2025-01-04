package myapplication.android.pixelpal.data.models.stores.store_link

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StoreLinksList(
    @SerialName("results") val items: List<StoreLink>
)