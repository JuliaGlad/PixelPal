package myapplication.android.pixelpal.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "platforms")
class PlatformEntity(
    val platformId: Long,
    val title: String,
    val image: String,
    val gamesCount: Int,
    val startYear: Int?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)