package myapplication.android.pixelpal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import myapplication.android.pixelpal.data.database.entities.StoreEntity

@Dao
interface StoreDao{

    @Query("SELECT * FROM stores")
    fun getAll(): List<StoreEntity>

    @Query("DELETE FROM stores")
    fun deleteAll()

    @Insert
    fun insertAll(stores: List<StoreEntity>)

}