package myapplication.android.pixelpal.domain.usecase.genres

import myapplication.android.pixelpal.data.repository.genres.GenresRepository
import myapplication.android.pixelpal.domain.model.genres.GenreDomain

class GetGenresUseCase(
    private val repository: GenresRepository
) {
    suspend fun invoke(): List<GenreDomain> = repository.getGenres()
}