package myapplication.android.pixelpal.data.repository.platforms

import myapplication.android.pixelpal.data.source.platform.PlatformLocalSource
import myapplication.android.pixelpal.data.source.platform.PlatformRemoteSource
import myapplication.android.pixelpal.domain.model.platform.PlatformDomainDetails
import myapplication.android.pixelpal.domain.model.platform.PlatformDomainList
import myapplication.android.pixelpal.domain.wrapper.platforms.toDomain
import javax.inject.Inject

class PlatformsRepositoryImpl @Inject constructor(
    private val localSource: PlatformLocalSource,
    private val remoteSource: PlatformRemoteSource
) : PlatformsRepository {

    override suspend fun getPlatformDetails(id: Int): PlatformDomainDetails =
        remoteSource.getPlatformDetails(id).toDomain()

    override suspend fun getPlatforms(page: Int): PlatformDomainList {
        val local = localSource.getPlatforms(page)
        val result =
            if (local != null) local
            else {
                val remote = remoteSource.getPlatforms(page)
                localSource.insertPlatforms(page, remote)
                remote
            }.toDomain()
        return result
    }

}