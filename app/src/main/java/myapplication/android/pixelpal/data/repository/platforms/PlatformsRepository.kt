package myapplication.android.pixelpal.data.repository.platforms

import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import myapplication.android.pixelpal.domain.model.platform.PlatformDomainList

interface PlatformsRepository {

    suspend fun getPlatforms(): PlatformDomainList

    fun getLocalPlatforms(): PlatformsList?
}