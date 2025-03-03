package myapplication.android.pixelpal.ui.platforms.fragments.platform.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepositoryImpl
import myapplication.android.pixelpal.data.source.platform.PlatformLocalSource
import myapplication.android.pixelpal.data.source.platform.PlatformLocalSourceImpl
import myapplication.android.pixelpal.data.source.platform.PlatformRemoteSource
import myapplication.android.pixelpal.data.source.platform.PlatformRemoteSourceImpl

@Module
interface PlatformModule {

    @PlatformScope
    @Binds
    fun bindPlatformsRepository(platformsRepositoryImpl: PlatformsRepositoryImpl): PlatformsRepository

    @PlatformScope
    @Binds
    fun bindPlatformsRemoteSource(platformRemoteSourceImpl: PlatformRemoteSourceImpl): PlatformRemoteSource

    @PlatformScope
    @Binds
    fun bindPlatformsLocalSource(platformLocalSourceImpl: PlatformLocalSourceImpl): PlatformLocalSource
}