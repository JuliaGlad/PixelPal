package myapplication.android.pixelpal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import myapplication.android.pixelpal.data.database.entities.GenreEntity

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres")
    fun getAll(): List<GenreEntity>

    @Query("DELETE FROM genres")
    fun deleteAll()

    @Insert
    fun insertAll(genres: List<GenreEntity>)

    @Update
    fun updateGenre(entity: GenreEntity)
}