package myapplication.android.pixelpal.data.models.screenshots

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ScreenshotsList(
    @SerialName("results") val items: List<Screenshot>
)