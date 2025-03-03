package myapplication.android.pixelpal.ui.platforms.fragments.platform.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformLocalDI

@Module
class PlatformLocalDIModule {

    @PlatformScope
    @Provides
    fun providePlatformLocalDI(
        platformsRepository: PlatformsRepository
    ): PlatformLocalDI = PlatformLocalDI(platformsRepository)

}