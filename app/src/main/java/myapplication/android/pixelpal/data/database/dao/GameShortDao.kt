package myapplication.android.pixelpal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import myapplication.android.pixelpal.data.database.entities.GamesShortEntity

@Dao
interface GameShortDao {

    @Query("SELECT * FROM gamesShortData")
    fun getAll(): List<GamesShortEntity>

    @Query("DELETE FROM gamesShortData")
    fun deleteAll()

    @Insert
    fun insertAll(games: List<GamesShortEntity>)
}