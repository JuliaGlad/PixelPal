package myapplication.android.pixelpal.data.repository.publishers

import myapplication.android.pixelpal.data.source.pulisher.PublisherLocalSource
import myapplication.android.pixelpal.data.source.pulisher.PublisherRemoteSource
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainDetails
import myapplication.android.pixelpal.domain.model.publishers.PublisherDomainList
import myapplication.android.pixelpal.domain.wrapper.publishers.toDomain
import javax.inject.Inject

class PublishersRepositoryImpl @Inject constructor(
    private val localSource: PublisherLocalSource,
    private val remoteSource: PublisherRemoteSource
) : PublishersRepository {
    override suspend fun getPublisherDetails(id: Long): PublisherDomainDetails =
        remoteSource.getPublisherDetails(id).toDomain()

    override suspend fun getPublishers(page: Int): PublisherDomainList {
        val local = localSource.getPublishers(page)
        val result =
            if (local != null) local
            else {
                val remote = remoteSource.getPublishers(page)
                localSource.insertPublishers(page,remote)
                remote
            }.toDomain()
        return result
    }

}