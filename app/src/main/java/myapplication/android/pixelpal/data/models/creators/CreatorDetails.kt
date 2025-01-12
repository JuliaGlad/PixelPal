package myapplication.android.pixelpal.data.models.creators

import kotlinx.serialization.Serializable

@Serializable
class CreatorDetails(
    val description: String,
    val rating: Float
)