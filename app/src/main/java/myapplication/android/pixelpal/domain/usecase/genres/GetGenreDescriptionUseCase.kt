package myapplication.android.pixelpal.domain.usecase.genres

import myapplication.android.pixelpal.data.repository.genres.GenresRepository
import myapplication.android.pixelpal.domain.model.genres.GenreDescriptionDomain

class GetGenreDescriptionUseCase(
    private val repository: GenresRepository
) {
    suspend fun invoke(id: Long): GenreDescriptionDomain = repository.getGenresDescription(id)
}