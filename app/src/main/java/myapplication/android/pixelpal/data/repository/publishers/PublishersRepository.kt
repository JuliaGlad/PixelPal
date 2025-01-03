package myapplication.android.pixelpal.data.repository.publishers

import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainList

interface PublishersRepository {

    suspend fun getPublishers(page: Int) : PublisherDomainList

}