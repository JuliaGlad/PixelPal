package myapplication.android.pixelpal.data.repository.platforms

import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import myapplication.android.pixelpal.data.source.platform.PlatformLocalSource
import myapplication.android.pixelpal.data.source.platform.PlatformRemoteSource
import myapplication.android.pixelpal.domain.model.platform.PlatformDomain
import myapplication.android.pixelpal.domain.wrapper.platforms.toDomain

class PlatformsRepositoryImpl(
    private val localSource: PlatformLocalSource,
    private val remoteSource: PlatformRemoteSource
) : PlatformsRepository {

    override suspend fun getPlatforms(): List<PlatformDomain> =
        (getLocalPlatforms() ?: remoteSource.getPlatforms()).toDomain()

    override fun getLocalPlatforms(): PlatformsList? = null
}