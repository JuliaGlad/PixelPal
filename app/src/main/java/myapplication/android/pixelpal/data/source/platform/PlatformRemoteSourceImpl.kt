package myapplication.android.pixelpal.data.source.platform

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import javax.inject.Inject

class PlatformRemoteSourceImpl @Inject constructor(
    private val api: GamesApi
): PlatformRemoteSource {
    override suspend fun getPlatforms(): PlatformsList =
        api.getPlatforms()
}