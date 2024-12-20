package myapplication.android.pixelpal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import myapplication.android.pixelpal.data.database.entities.PublisherEntity

@Dao
interface PublisherDao {

    @Query("SELECT * FROM publishers")
    fun getAll(): List<PublisherEntity>

    @Query("DELETE FROM publishers")
    fun deleteAll()

    @Insert
    fun insertAll(publishers: List<PublisherEntity>)
}