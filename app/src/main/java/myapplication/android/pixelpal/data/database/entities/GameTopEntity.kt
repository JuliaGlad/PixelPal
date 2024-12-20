package myapplication.android.pixelpal.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.json.JsonArray
import myapplication.android.pixelpal.data.models.GameRating

@Entity(tableName = "gameTop")
class GameTopEntity(
    val gameId: Long,
    val title: String,
    val releaseDate: String?,
    val image: String?,
    val rating: Float?,
    val ageRating: GameRating?,
    val genres: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)