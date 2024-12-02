package myapplication.android.pixelpal.data.source.genres

import myapplication.android.pixelpal.data.models.genres.GenreDescription
import myapplication.android.pixelpal.data.models.genres.GenresList

interface GenresRemoteSource {

    suspend fun getGenresDescription(id: Long): GenreDescription

    suspend fun getGenresData(): GenresList

}