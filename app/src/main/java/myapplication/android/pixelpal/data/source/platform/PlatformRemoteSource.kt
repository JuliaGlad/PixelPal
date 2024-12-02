package myapplication.android.pixelpal.data.source.platform

import myapplication.android.pixelpal.data.models.platforms.PlatformsList

interface PlatformRemoteSource {

    suspend fun getPlatforms(): PlatformsList

}