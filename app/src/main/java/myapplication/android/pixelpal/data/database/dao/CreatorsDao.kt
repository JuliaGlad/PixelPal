package myapplication.android.pixelpal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import myapplication.android.pixelpal.data.database.entities.CreatorEntity

@Dao
interface CreatorsDao {

    @Query("SELECT * FROM creators")
    fun getAll(): List<CreatorEntity>

    @Query("DELETE FROM creators")
    fun deleteAll()

    @Insert
    fun insertAll(creators: List<CreatorEntity>)
}