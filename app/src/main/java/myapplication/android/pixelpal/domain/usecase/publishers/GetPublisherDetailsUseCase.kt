package myapplication.android.pixelpal.domain.usecase.publishers

import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainDetails
import javax.inject.Inject

class GetPublisherDetailsUseCase @Inject constructor(
   private val publishersRepository: PublishersRepository
) {

    suspend fun invoke(id: Long): PublisherDomainDetails = publishersRepository.getPublisherDetails(id)

}