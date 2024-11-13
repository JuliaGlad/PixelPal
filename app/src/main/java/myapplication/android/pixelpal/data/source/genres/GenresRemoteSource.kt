package myapplication.android.pixelpal.data.source.genres

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.genres.description.GenreDescription
import myapplication.android.pixelpal.data.models.genres.genres.GenresList

class GenresRemoteSource(
    private val api: GamesApi
) {
    suspend fun getGenresDescription(id: Long): GenreDescription =
        api.getGenreDescription(id)

    suspend fun getGenresData(): GenresList =
        api.getGenresData()
}