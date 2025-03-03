package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi.PlatformDetailsLocalDI

@Module
class PlatformDetailsLocalDIModule {

    @PlatformDetailsScope
    @Provides
    fun providePlatformDetailsLocalDI(
        platformsRepository: PlatformsRepository,
        gamesRepository: GamesRepository
    ): PlatformDetailsLocalDI = PlatformDetailsLocalDI(
        platformsRepository,
        gamesRepository
    )

}