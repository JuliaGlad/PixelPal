package myapplication.android.pixelpal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import myapplication.android.pixelpal.data.database.entities.GameReleaseEntity
import myapplication.android.pixelpal.data.database.entities.GameTopEntity

@Dao
interface GamesReleaseDao {

    @Query("SELECT * FROM gameReleases")
    fun getAll(): List<GameReleaseEntity>

    @Query("DELETE FROM gameReleases")
    fun deleteAll()

    @Insert
    fun insertAll(games: List<GameReleaseEntity>)

}