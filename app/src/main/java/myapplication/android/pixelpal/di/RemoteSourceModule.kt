package myapplication.android.pixelpal.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSource
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSourceImpl
import myapplication.android.pixelpal.data.source.favorite_games.FavoriteGamesRemoteSource
import myapplication.android.pixelpal.data.source.favorite_games.FavoriteGamesRemoteSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSourceImpl
import myapplication.android.pixelpal.data.source.genres.GenresRemoteSource
import myapplication.android.pixelpal.data.source.genres.GenresRemoteSourceImpl
import myapplication.android.pixelpal.data.source.platform.PlatformRemoteSource
import myapplication.android.pixelpal.data.source.platform.PlatformRemoteSourceImpl
import myapplication.android.pixelpal.data.source.pulisher.PublisherRemoteSource
import myapplication.android.pixelpal.data.source.pulisher.PublishersRemoteSourceImpl
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSource
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSourceImpl
import javax.inject.Singleton

@Module
interface RemoteSourceModule {
    @Singleton
    @Binds
    fun bindRemoteSourceFavoriteGames(favoriteGamesRemoteSourceImpl: FavoriteGamesRemoteSourceImpl): FavoriteGamesRemoteSource

    @Singleton
    @Binds
    fun bindRemoteSourceCreators(creatorsRemoteSourceImpl: CreatorsRemoteSourceImpl) : CreatorsRemoteSource

    @Singleton
    @Binds
    fun bindRemoteSourceGames(gamesRemoteSourceImpl: GamesRemoteSourceImpl) : GamesRemoteSource

    @Singleton
    @Binds
    fun bindRemoteSourceGenres(genresRemoteSourceImpl: GenresRemoteSourceImpl) : GenresRemoteSource

    @Singleton
    @Binds
    fun bindRemoteSourcePlatform(platformRemoteSourceImpl: PlatformRemoteSourceImpl) : PlatformRemoteSource

    @Singleton
    @Binds
    fun bindRemoteSourcePublisher(publishersRemoteSourceImpl: PublishersRemoteSourceImpl) : PublisherRemoteSource

    @Singleton
    @Binds
    fun bindRemoteSourceStores(storesRemoteSourceImpl: StoresRemoteSourceImpl) : StoresRemoteSource
}