package myapplication.android.pixelpal.data.repository.genres

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.data.database.entities.GenreEntity
import myapplication.android.pixelpal.data.models.genres.GenreDescription
import myapplication.android.pixelpal.data.repository.dto.genre.GenreDescriptionDto
import myapplication.android.pixelpal.data.repository.dto.genre.GenreDtoList
import myapplication.android.pixelpal.data.repository.getAndCheckData
import myapplication.android.pixelpal.data.repository.mapper.genres.toDto
import myapplication.android.pixelpal.data.source.genres.GenresLocalSource
import myapplication.android.pixelpal.data.source.genres.GenresRemoteSource
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val localSource: GenresLocalSource,
    private val remoteSource: GenresRemoteSource
) : GenresRepository {
    override suspend fun getGenres(): GenreDtoList =
        getAndCheckData(
            localSource::getGenres,
            remoteSource::getGenresData,
            localSource::insertGenres
        ).toDto()

    override suspend fun getGenresDescription(id: Long): GenreDescriptionDto {
        val genres = localSource.getGenreEntities()
        var chosenGenre: GenreEntity? = null
        var description: GenreDescription? = null
        if (genres != null) {
            for (i in genres) {
                if (i.genreId == id) {
                    chosenGenre = i
                    break
                }
            }
            if (chosenGenre?.description != null) description = GenreDescription(chosenGenre.description!!)
        }
        if (description == null) description = withContext(Dispatchers.IO){
            remoteSource.getGenresDescription(id)
        }
        return description.toDto()
    }

}
