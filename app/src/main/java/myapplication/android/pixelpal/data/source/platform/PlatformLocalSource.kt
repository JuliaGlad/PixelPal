package myapplication.android.pixelpal.data.source.platform

import myapplication.android.pixelpal.data.models.platforms.PlatformsList

interface PlatformLocalSource {

    fun getPlatforms(page: Int) : PlatformsList?

    fun deletePlatforms()

    fun insertPlatforms(currentPage: Int, platforms: PlatformsList)
}