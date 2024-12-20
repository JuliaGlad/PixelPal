package myapplication.android.pixelpal.data.repository.publishers

import myapplication.android.pixelpal.data.models.publishers.PublishersList
import myapplication.android.pixelpal.data.repository.getAndCheckData
import myapplication.android.pixelpal.data.source.pulisher.PublisherLocalSource
import myapplication.android.pixelpal.data.source.pulisher.PublisherRemoteSource
import myapplication.android.pixelpal.data.source.pulisher.PublishersLocalSourceImpl
import myapplication.android.pixelpal.data.source.pulisher.PublishersRemoteSourceImpl
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainList
import myapplication.android.pixelpal.domain.wrapper.publishers.toDomain
import javax.inject.Inject

class PublishersRepositoryImpl @Inject constructor(
    private val localSource: PublisherLocalSource,
    private val remoteSource: PublisherRemoteSource
) : PublishersRepository {

    override suspend fun getPublishers(): PublisherDomainList =
        getAndCheckData(
            localSource::getPublishers,
            remoteSource::getPublishers,
            localSource::insertPublishers
        ).toDomain()

}