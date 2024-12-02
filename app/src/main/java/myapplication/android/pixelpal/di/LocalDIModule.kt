package myapplication.android.pixelpal.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.genres.GenresRepository
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsLocalDI
import myapplication.android.pixelpal.ui.games.games.mvi.GamesLocalDI
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesLocalDI
import myapplication.android.pixelpal.ui.home.mvi.HomeLocalDI
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformLocalDI
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresLocalDI

@Module
class LocalDIModule {

    @Provides
    fun provideStoresLocalDI(
        storesRepository: StoresRepository
    ): StoresLocalDI = StoresLocalDI(storesRepository)

    @Provides
    fun provideCreatorsLocalDI(
        creatorsRepository: CreatorsRepository,
        publishersRepository: PublishersRepository
    ): CreatorsLocalDI = CreatorsLocalDI(creatorsRepository, publishersRepository)

    @Provides
    fun provideMainGamesLocalDI(
        genresRepository: GenresRepository
    ): MainGamesLocalDI = MainGamesLocalDI(genresRepository)

    @Provides
    fun provideGamesLocalDI(
        gamesRepository: GamesRepository
    ): GamesLocalDI = GamesLocalDI(gamesRepository)

    @Provides
    fun provideHomeLocalDI(
        gamesRepository: GamesRepository
    ): HomeLocalDI = HomeLocalDI(gamesRepository)

    @Provides
    fun providePlatformLocalDI(
        platformsRepository: PlatformsRepository
    ): PlatformLocalDI = PlatformLocalDI(platformsRepository)
}