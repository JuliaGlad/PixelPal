package myapplication.android.pixelpal.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
class StoreEntity(
    val storeId: Int,
    val page: Int,
    val image: String,
    val domain: String?,
    val name: String,
    val projects: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)