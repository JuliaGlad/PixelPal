package myapplication.android.pixelpal.data.repository.genres

import myapplication.android.pixelpal.data.models.genres.description.GenreDescription
import myapplication.android.pixelpal.data.models.genres.genres.GenresList
import myapplication.android.pixelpal.domain.model.genres.GenreDescriptionDomain
import myapplication.android.pixelpal.domain.model.genres.GenreDomain

interface GenresRepository {

    suspend fun getGenres(): List<GenreDomain>

    suspend fun getGenresDescription(id: Long): GenreDescriptionDomain

    fun getLocalGenres(): GenresList?

    fun getLocalGenresDescription(id: Long) : GenreDescription?
}