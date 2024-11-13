package myapplication.android.pixelpal.data.models.genres.genres

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GenresList(
    @SerialName("results") val items: List<myapplication.android.pixelpal.data.models.genres.genres.Genre>
)