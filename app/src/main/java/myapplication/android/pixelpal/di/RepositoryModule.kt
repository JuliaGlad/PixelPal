package myapplication.android.pixelpal.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepositoryImpl
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepositoryImpl
import myapplication.android.pixelpal.data.repository.genres.GenresRepository
import myapplication.android.pixelpal.data.repository.genres.GenresRepositoryImpl
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepositoryImpl
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepositoryImpl
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.data.repository.stores.StoresRepositoryImpl
import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.data.repository.user.UserRepositoryImpl
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindUsersRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    fun bindCreatorsRepository(creatorsRepositoryImpl: CreatorsRepositoryImpl): CreatorsRepository

    @Singleton
    @Binds
    fun bindGamesRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository

    @Singleton
    @Binds
    fun bindGenresRepository(genresRepositoryImpl: GenresRepositoryImpl): GenresRepository

    @Singleton
    @Binds
    fun bindPlatformRepository(platformRepositoryImpl: PlatformsRepositoryImpl): PlatformsRepository

    @Singleton
    @Binds
    fun bindPublishersRepository(publishersRepositoryImpl: PublishersRepositoryImpl): PublishersRepository

    @Singleton
    @Binds
    fun bindStoresRepository(storeRepositoryImpl: StoresRepositoryImpl): StoresRepository
}