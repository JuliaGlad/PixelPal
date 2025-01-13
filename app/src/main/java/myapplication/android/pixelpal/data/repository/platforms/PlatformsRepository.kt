package myapplication.android.pixelpal.data.repository.platforms

import myapplication.android.pixelpal.data.models.platforms.PlatformDetails
import myapplication.android.pixelpal.domain.model.platform.PlatformDomainDetails
import myapplication.android.pixelpal.domain.model.platform.PlatformDomainList

interface PlatformsRepository {

    suspend fun getPlatformDetails(id: Int): PlatformDomainDetails

    suspend fun getPlatforms(page: Int): PlatformDomainList
}