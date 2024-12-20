package myapplication.android.pixelpal.data.source.genres

import myapplication.android.pixelpal.data.database.entities.GenreEntity
import myapplication.android.pixelpal.data.database.provider.GenreProvider
import myapplication.android.pixelpal.data.models.genres.Genre
import myapplication.android.pixelpal.data.models.genres.GenresList
import java.util.stream.Collectors
import javax.inject.Inject

class GenresLocalSourceImpl @Inject constructor() : GenresLocalSource {

    override fun getGenres(): GenresList? {
        val data = GenreProvider().getGenres()
        return if (data.isNotEmpty()) {
            GenresList(
                data.stream()
                    .map { it.toGenre() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun insertGenres(genres: GenresList) {
        GenreProvider().insertGenres(genres)
    }

    override fun deleteGenres() {
        GenreProvider().deleteGenres()
    }

    override fun getGenreEntities(): List<GenreEntity>? {
        val data = GenreProvider().getGenres()
        return data.ifEmpty { null }
    }

    override fun updateGenre(entity: GenreEntity, description: String) {
        GenreProvider().updateGenres(entity, description)
    }

    private fun GenreEntity.toGenre() = Genre(genreId, title)
}