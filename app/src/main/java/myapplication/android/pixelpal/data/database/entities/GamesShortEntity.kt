package myapplication.android.pixelpal.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import myapplication.android.pixelpal.data.models.GameRating

@Entity(tableName = "gamesShortData")
class GamesShortEntity(
    val gameId: Long,
    val page: Int,
    val genre: Long,
    val title: String,
    val playTime: Int,
    val releaseDate: String?,
    val image: String?,
    val ageRating: GameRating?,
    val rating: Int?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
