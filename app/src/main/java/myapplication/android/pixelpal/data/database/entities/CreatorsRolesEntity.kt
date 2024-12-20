package myapplication.android.pixelpal.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "creatorsRole")
class CreatorsRolesEntity(
    val roleId: Int,
    val title: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)