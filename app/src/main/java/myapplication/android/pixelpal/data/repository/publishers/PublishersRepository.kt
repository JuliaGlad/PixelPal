package myapplication.android.pixelpal.data.repository.publishers

import myapplication.android.pixelpal.data.models.publishers.PublishersList
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomain

interface PublishersRepository {

    suspend fun getPublishers() : List<PublisherDomain>

    fun getLocalPublishers(): PublishersList?
}