package myapplication.android.pixelpal.data.repository.genres

import myapplication.android.pixelpal.data.repository.dto.genre.GenreDescriptionDto
import myapplication.android.pixelpal.data.repository.dto.genre.GenreDtoList

interface GenresRepository {

    suspend fun getGenres(): GenreDtoList

    suspend fun getGenresDescription(id: Long): GenreDescriptionDto

}