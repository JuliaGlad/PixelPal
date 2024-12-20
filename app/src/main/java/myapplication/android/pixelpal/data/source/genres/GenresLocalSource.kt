package myapplication.android.pixelpal.data.source.genres

import myapplication.android.pixelpal.data.database.entities.GenreEntity
import myapplication.android.pixelpal.data.models.genres.GenresList

interface GenresLocalSource {

    fun getGenres(): GenresList?

    fun insertGenres(genres: GenresList)

    fun deleteGenres()

    fun getGenreEntities(): List<GenreEntity>?

    fun updateGenre(entity: GenreEntity, description: String)
}