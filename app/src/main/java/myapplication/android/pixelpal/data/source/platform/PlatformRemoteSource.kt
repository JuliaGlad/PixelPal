package myapplication.android.pixelpal.data.source.platform

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.platforms.PlatformsList

class PlatformRemoteSource(
    private val api: GamesApi
) {
    suspend fun getPlatforms(): PlatformsList =
        api.getPlatforms()
}