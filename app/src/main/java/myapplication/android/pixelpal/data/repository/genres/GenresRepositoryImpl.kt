package myapplication.android.pixelpal.data.repository.genres

import myapplication.android.pixelpal.data.models.genres.description.GenreDescription
import myapplication.android.pixelpal.data.models.genres.genres.GenresList
import myapplication.android.pixelpal.data.source.genres.GenresLocalSource
import myapplication.android.pixelpal.data.source.genres.GenresRemoteSource
import myapplication.android.pixelpal.domain.model.genres.GenreDescriptionDomain
import myapplication.android.pixelpal.domain.model.genres.GenreDomain
import myapplication.android.pixelpal.domain.wrapper.genres.toDomain

class GenresRepositoryImpl(
    private val remoteSource: GenresRemoteSource,
    private val localSource: GenresLocalSource
) : GenresRepository {
    override suspend fun getGenres(): List<GenreDomain> =
        (getLocalGenres() ?: remoteSource.getGenresData()).toDomain()

    override suspend fun getGenresDescription(id: Long): GenreDescriptionDomain =
        (getLocalGenresDescription(id) ?: remoteSource.getGenresDescription(id)).toDomain()

    override fun getLocalGenres(): GenresList? = null

    override fun getLocalGenresDescription(id: Long): GenreDescription? = null

}