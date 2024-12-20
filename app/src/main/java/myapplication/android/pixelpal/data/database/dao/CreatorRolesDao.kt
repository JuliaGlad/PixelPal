package myapplication.android.pixelpal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import myapplication.android.pixelpal.data.database.entities.CreatorsRolesEntity

@Dao
interface CreatorRolesDao {

    @Query("SELECT * FROM creatorsRole")
    fun getAll(): List<CreatorsRolesEntity>

    @Query("DELETE FROM creatorsRole")
    fun deleteAll()

    @Insert
    fun insertAll(roles: List<CreatorsRolesEntity>)
}