package myapplication.android.pixelpal.data.source.genres

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.genres.GenreDescription
import myapplication.android.pixelpal.data.models.genres.GenresList
import javax.inject.Inject

class GenresRemoteSourceImpl @Inject constructor(
    private val api: GamesApi
): GenresRemoteSource {
    override suspend fun getGenresDescription(id: Long): GenreDescription =
        api.getGenreDescription(id)

    override suspend fun getGenresData(): GenresList =
        api.getGenresData()
}