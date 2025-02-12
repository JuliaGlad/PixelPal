package myapplication.android.pixelpal.data.source.platform

import myapplication.android.pixelpal.data.api.GamesApi
import myapplication.android.pixelpal.data.models.platforms.PlatformDetails
import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import javax.inject.Inject

class PlatformRemoteSourceImpl @Inject constructor(
    private val api: GamesApi
): PlatformRemoteSource {
    override suspend fun getPlatformDetails(id: Long): PlatformDetails =
        api.getPlatformDetails(id)

    override suspend fun getPlatforms(page: Int): PlatformsList =
        api.getPlatforms(page)
}