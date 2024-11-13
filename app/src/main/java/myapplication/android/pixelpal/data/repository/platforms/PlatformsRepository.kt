package myapplication.android.pixelpal.data.repository.platforms

import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import myapplication.android.pixelpal.domain.model.platform.PlatformDomain

interface PlatformsRepository {

    suspend fun getPlatforms(): List<PlatformDomain>

    fun getLocalPlatforms(): PlatformsList?
}