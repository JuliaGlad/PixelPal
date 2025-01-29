package myapplication.android.pixelpal.data.repository.publishers

import myapplication.android.pixelpal.data.repository.dto.publisher.PublisherDetailsDto
import myapplication.android.pixelpal.data.repository.dto.publisher.PublisherDtoList
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainDetails
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainList

interface PublishersRepository {

    suspend fun getPublisherDetails(id: Long): PublisherDetailsDto

    suspend fun getPublishers(page: Int) : PublisherDtoList

}