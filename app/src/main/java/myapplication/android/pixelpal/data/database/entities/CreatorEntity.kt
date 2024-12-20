package myapplication.android.pixelpal.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import myapplication.android.pixelpal.data.models.creators_roles.Role

@Entity(tableName = "creators")
class CreatorEntity(
    val creatorId: Long,
    val name: String,
    val roles: List<Role>,
    val gamesCount: Int,
    val image: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)