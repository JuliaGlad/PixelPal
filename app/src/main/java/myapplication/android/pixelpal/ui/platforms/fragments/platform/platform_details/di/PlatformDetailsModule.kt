package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepositoryImpl
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepositoryImpl
import myapplication.android.pixelpal.data.source.games.GamesLocalSource
import myapplication.android.pixelpal.data.source.games.GamesLocalSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSource
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSourceImpl
import myapplication.android.pixelpal.data.source.platform.PlatformLocalSource
import myapplication.android.pixelpal.data.source.platform.PlatformLocalSourceImpl
import myapplication.android.pixelpal.data.source.platform.PlatformRemoteSource
import myapplication.android.pixelpal.data.source.platform.PlatformRemoteSourceImpl
import myapplication.android.pixelpal.ui.home.di.HomeScope

@Module
interface PlatformDetailsModule {

    @PlatformDetailsScope
    @Binds
    fun bindPlatformsRepository(platformsRepositoryImpl: PlatformsRepositoryImpl): PlatformsRepository

    @PlatformDetailsScope
    @Binds
    fun bindPlatformsRemoteSource(platformRemoteSourceImpl: PlatformRemoteSourceImpl): PlatformRemoteSource

    @PlatformDetailsScope
    @Binds
    fun bindPlatformsLocalSource(platformLocalSourceImpl: PlatformLocalSourceImpl): PlatformLocalSource

    @PlatformDetailsScope
    @Binds
    fun bindGamesRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository

    @PlatformDetailsScope
    @Binds
    fun bindGamesRemoteSource(gamesRemoteSourceImpl: GamesRemoteSourceImpl): GamesRemoteSource

    @PlatformDetailsScope
    @Binds
    fun bindGamesLocalSource(gamesLocalSourceImpl: GamesLocalSourceImpl): GamesLocalSource

    @PlatformDetailsScope
    @Binds
    fun bindGamesShortDataLocalSource(gamesShortDataLocalSourceImpl: GamesShortDataLocalSourceImpl): GamesShortDataLocalSource
}