package myapplication.android.pixelpal.data.repository.publishers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.data.repository.dto.publisher.PublisherDetailsDto
import myapplication.android.pixelpal.data.repository.dto.publisher.PublisherDtoList
import myapplication.android.pixelpal.data.repository.mapper.publisher.toDto
import myapplication.android.pixelpal.data.source.pulisher.PublisherLocalSource
import myapplication.android.pixelpal.data.source.pulisher.PublisherRemoteSource
import javax.inject.Inject

class PublishersRepositoryImpl @Inject constructor(
    private val localSource: PublisherLocalSource,
    private val remoteSource: PublisherRemoteSource
) : PublishersRepository {
    override suspend fun getPublisherDetails(id: Long): PublisherDetailsDto =
        withContext(Dispatchers.IO) {
            remoteSource.getPublisherDetails(id).toDto()
        }

    override suspend fun getPublishers(page: Int): PublisherDtoList {
        val local = localSource.getPublishers(page)
        val result =
            if (local != null) local
            else {
                val remote = withContext(Dispatchers.IO) {
                    remoteSource.getPublishers(page)
                }
                localSource.insertPublishers(page, remote)
                remote
            }
        return result.toDto()
    }

}