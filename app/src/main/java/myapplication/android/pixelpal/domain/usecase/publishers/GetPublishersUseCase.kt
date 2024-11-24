package myapplication.android.pixelpal.domain.usecase.publishers

import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainList

class GetPublishersUseCase(
    private val repository: PublishersRepository
) {
    suspend fun invoke(): PublisherDomainList = repository.getPublishers()
}