package myapplication.android.pixelpal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import myapplication.android.pixelpal.data.database.entities.PlatformEntity

@Dao
interface PlatformDao {

    @Query("SELECT * FROM platforms")
    fun getAll(): List<PlatformEntity>

    @Query("DELETE FROM platforms")
    fun deleteAll()

    @Insert
    fun insertAll(platforms: List<PlatformEntity>)

}