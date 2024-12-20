package myapplication.android.pixelpal.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
class GenreEntity(
    val genreId: Long,
    val title: String,
    var description: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)