package myapplication.android.pixelpal.data.repository.publishers

import myapplication.android.pixelpal.data.models.publishers.PublishersList
import myapplication.android.pixelpal.data.source.pulisher.PublishersLocalSource
import myapplication.android.pixelpal.data.source.pulisher.PublishersRemoteSource
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomain
import myapplication.android.pixelpal.domain.wrapper.publishers.toDomain

class PublishersRepositoryImpl(
    private val remoteSource: PublishersRemoteSource,
    private val localSource: PublishersLocalSource
) : PublishersRepository {

    override suspend fun getPublishers() : List<PublisherDomain> =
        (getLocalPublishers() ?: remoteSource.getPublishers()).toDomain()

    override fun getLocalPublishers(): PublishersList? = null
}