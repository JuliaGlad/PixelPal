package myapplication.android.pixelpal.domain.usecase.genres

import myapplication.android.pixelpal.data.repository.genres.GenresRepository
import myapplication.android.pixelpal.domain.mapper.genres.toDomain
import myapplication.android.pixelpal.domain.model.genres.GenreDomainList
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: GenresRepository
) {
    suspend fun invoke(): GenreDomainList =
        repository.getGenres().toDomain()
}