package myapplication.android.pixelpal.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import myapplication.android.pixelpal.data.models.GameRating

@Entity(tableName = "favoriteGames")
class FavoriteGamesEntity(
    val gameId: Long,
    val title: String,
    val playTime: Int,
    val genre: String,
    val releaseDate: String?,
    val image: String?,
    val ageRating: GameRating?,
    val rating:Float?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)