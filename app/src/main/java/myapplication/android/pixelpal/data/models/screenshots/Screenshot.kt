package myapplication.android.pixelpal.data.models.screenshots

import kotlinx.serialization.Serializable

@Serializable
class Screenshot(
    val id: Long,
    val image: String
)