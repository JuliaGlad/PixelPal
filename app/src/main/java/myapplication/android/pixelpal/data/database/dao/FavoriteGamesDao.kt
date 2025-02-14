package myapplication.android.pixelpal.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import myapplication.android.pixelpal.data.database.entities.FavoriteGamesEntity

@Dao
interface FavoriteGamesDao {

    @Query("SELECT * FROM favoriteGames")
    fun getGames(): List<FavoriteGamesEntity>

    @Delete
    fun deleteGame(game: FavoriteGamesEntity)

    @Query("DELETE FROM favoriteGames")
    fun deleteAll()

    @Insert
    fun insertAll(games: List<FavoriteGamesEntity>)

    @Insert
    fun insertGame(game: FavoriteGamesEntity)
}