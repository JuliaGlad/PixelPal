package myapplication.android.pixelpal.data.source.platform

import myapplication.android.pixelpal.data.database.entities.PlatformEntity
import myapplication.android.pixelpal.data.database.provider.PlatformProvider
import myapplication.android.pixelpal.data.models.platforms.Platform
import myapplication.android.pixelpal.data.models.platforms.PlatformsList
import java.util.stream.Collectors
import javax.inject.Inject

class PlatformLocalSourceImpl @Inject constructor() : PlatformLocalSource {
    override fun getPlatforms(): PlatformsList? {
        val data = PlatformProvider().getPlatforms()
        return if (data.isNotEmpty()) {
            PlatformsList(
                data.stream()
                    .map { it.toPlatform() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun deletePlatforms() {
        PlatformProvider().deletePlatforms()
    }

    override fun insertPlatforms(platforms: PlatformsList) {
        PlatformProvider().insertPlatforms(platforms)
    }

    private fun PlatformEntity.toPlatform() =
        Platform(image, gamesCount, startYear, platformId, title)
}