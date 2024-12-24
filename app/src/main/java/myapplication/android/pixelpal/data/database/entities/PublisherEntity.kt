package myapplication.android.pixelpal.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "publishers")
class PublisherEntity(
    val publisherId: Long,
    val page: Int,
    val title: String,
    val gamesCount: Int,
    val image: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)