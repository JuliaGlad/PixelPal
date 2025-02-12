package myapplication.android.pixelpal.data.repository.platforms

import myapplication.android.pixelpal.data.repository.dto.platforms.PlatformDtoDetails
import myapplication.android.pixelpal.data.repository.dto.platforms.PlatformDtoList

interface PlatformsRepository {

    suspend fun getPlatformDetails(id: Long): PlatformDtoDetails

    suspend fun getPlatforms(page: Int): PlatformDtoList
}