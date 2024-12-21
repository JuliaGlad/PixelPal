package myapplication.android.pixelpal.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import myapplication.android.pixelpal.data.models.GameRating

@Entity(tableName = "gameReleases")
class GameReleaseEntity(
    val gameId: Long,
    val title: String,
    val releaseFullDate: String?,
    val releaseDate: Int,
    val monthNumber: Int,
    val image: String?,
    val rating: Float?,
    val ageRating: GameRating?,
    val genres: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

)