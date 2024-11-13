package myapplication.android.pixelpal.data.models.publishers

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PublishersList(
    @SerialName("results") val items: List<Publisher>
)