package myapplication.android.pixelpal.domain.usecase.publishers

import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainList
import javax.inject.Inject

class GetPublishersUseCase @Inject constructor(
    private val repository: PublishersRepository
) {
    suspend fun invoke(): PublisherDomainList = repository.getPublishers()
}