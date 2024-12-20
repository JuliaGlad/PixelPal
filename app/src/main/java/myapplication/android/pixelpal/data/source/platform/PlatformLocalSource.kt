package myapplication.android.pixelpal.data.source.platform

import myapplication.android.pixelpal.data.models.platforms.PlatformsList

interface PlatformLocalSource {

    fun getPlatforms() : PlatformsList?

    fun deletePlatforms()

    fun insertPlatforms(platforms: PlatformsList)
}