package myapplication.android.pixelpal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import myapplication.android.pixelpal.data.database.entities.GameTopEntity

@Dao
interface GameTopDao {

    @Query("SELECT * FROM gameTop")
    fun getAll(): List<GameTopEntity>

    @Query("DELETE FROM gameTop")
    fun deleteAll()

    @Insert
    fun insertAll(games: List<GameTopEntity>)
}