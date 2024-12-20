package myapplication.android.pixelpal.data.repository.platforms

import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import myapplication.android.pixelpal.data.repository.getAndCheckData
import myapplication.android.pixelpal.data.source.platform.PlatformLocalSource
import myapplication.android.pixelpal.data.source.platform.PlatformLocalSourceImpl
import myapplication.android.pixelpal.data.source.platform.PlatformRemoteSource
import myapplication.android.pixelpal.data.source.platform.PlatformRemoteSourceImpl
import myapplication.android.pixelpal.domain.model.platform.PlatformDomainList
import myapplication.android.pixelpal.domain.wrapper.platforms.toDomain
import javax.inject.Inject

class PlatformsRepositoryImpl @Inject constructor(
    private val localSource: PlatformLocalSource,
    private val remoteSource: PlatformRemoteSource
) : PlatformsRepository {

    override suspend fun getPlatforms(): PlatformDomainList =
        getAndCheckData(
            localSource::getPlatforms,
            remoteSource::getPlatforms,
            localSource::insertPlatforms
        ).toDomain()

}