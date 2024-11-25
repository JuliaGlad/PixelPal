package myapplication.android.pixelpal.data.repository.genres

import myapplication.android.pixelpal.data.models.genres.GenreDescription
import myapplication.android.pixelpal.data.models.genres.GenresList
import myapplication.android.pixelpal.domain.model.genres.GenreDescriptionDomain
import myapplication.android.pixelpal.domain.model.genres.GenreDomainList

interface GenresRepository {

    suspend fun getGenres(): GenreDomainList

    suspend fun getGenresDescription(id: Long): GenreDescriptionDomain

    fun getLocalGenres(): GenresList?

    fun getLocalGenresDescription(id: Long) : GenreDescription?
}