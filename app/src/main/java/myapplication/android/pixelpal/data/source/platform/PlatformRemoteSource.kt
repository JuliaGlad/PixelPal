package myapplication.android.pixelpal.data.source.platform

import myapplication.android.pixelpal.data.models.platforms.PlatformDetails
import myapplication.android.pixelpal.data.models.platforms.PlatformsList

interface PlatformRemoteSource {

    suspend fun getPlatformDetails(id: Int): PlatformDetails

    suspend fun getPlatforms(page: Int): PlatformsList

}