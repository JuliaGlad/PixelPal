package myapplication.android.pixelpal.data.repository.publishers

import myapplication.android.pixelpal.data.models.publishers.PublishersList
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainList

interface PublishersRepository {

    suspend fun getPublishers() : PublisherDomainList

    fun getLocalPublishers(): PublishersList?
}