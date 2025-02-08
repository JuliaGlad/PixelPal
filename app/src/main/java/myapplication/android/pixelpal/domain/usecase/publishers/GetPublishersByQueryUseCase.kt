package myapplication.android.pixelpal.domain.usecase.publishers

import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.domain.mapper.publishers.toDomain
import javax.inject.Inject

class GetPublishersByQueryUseCase @Inject constructor(
    private val publishersRepository: PublishersRepository
) {
    suspend fun invoke(page: Int, query: String) =
        publishersRepository.getPublishers(page).toDomain(query)
}