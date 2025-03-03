package myapplication.android.pixelpal.ui.game_details.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepositoryImpl
import myapplication.android.pixelpal.data.repository.favorite_games.FavoriteGamesRepository
import myapplication.android.pixelpal.data.repository.favorite_games.FavoriteGamesRepositoryImpl
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepositoryImpl
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.data.repository.stores.StoresRepositoryImpl
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSource
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSourceImpl
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSource
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSourceImpl
import myapplication.android.pixelpal.data.source.favorite_games.FavoriteGamesLocalSource
import myapplication.android.pixelpal.data.source.favorite_games.FavoriteGamesLocalSourceImpl
import myapplication.android.pixelpal.data.source.favorite_games.FavoriteGamesRemoteSource
import myapplication.android.pixelpal.data.source.favorite_games.FavoriteGamesRemoteSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesLocalSource
import myapplication.android.pixelpal.data.source.games.GamesLocalSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSource
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSourceImpl
import myapplication.android.pixelpal.data.source.stores.StoresLocalSource
import myapplication.android.pixelpal.data.source.stores.StoresLocalSourceImpl
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSource
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSourceImpl
import myapplication.android.pixelpal.ui.all_creators.di.AllCreatorsScope

@Module
interface GameDetailsModule {

    @GameDetailsScope
    @Binds
    fun bindFavoriteGamesRepository(favoriteGamesRepositoryImpl: FavoriteGamesRepositoryImpl): FavoriteGamesRepository

    @GameDetailsScope
    @Binds
    fun bindFavoriteGamesRemoteSource(favoriteGamesRemoteSourceImpl: FavoriteGamesRemoteSourceImpl): FavoriteGamesRemoteSource

    @GameDetailsScope
    @Binds
    fun bindFavoriteGamesLocalSource(favoriteGamesLocalSourceImpl: FavoriteGamesLocalSourceImpl): FavoriteGamesLocalSource

    @GameDetailsScope
    @Binds
    fun bindStoresRepository(storesRepositoryImpl: StoresRepositoryImpl): StoresRepository

    @GameDetailsScope
    @Binds
    fun bindStoresLocalSource(storesLocalSourceImpl: StoresLocalSourceImpl): StoresLocalSource

    @GameDetailsScope
    @Binds
    fun bindStoresRemoteSource(storeRemoteSourceImpl: StoresRemoteSourceImpl): StoresRemoteSource

    @GameDetailsScope
    @Binds
    fun bindGamesRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository

    @GameDetailsScope
    @Binds
    fun bindGamesRemoteSource(gamesRemoteSourceImpl: GamesRemoteSourceImpl): GamesRemoteSource

    @GameDetailsScope
    @Binds
    fun bindGamesLocalSource(gamesLocalSourceImpl: GamesLocalSourceImpl): GamesLocalSource

    @GameDetailsScope
    @Binds
    fun bindCreatorsRepository(creatorsRepositoryImpl: CreatorsRepositoryImpl): CreatorsRepository

    @GameDetailsScope
    @Binds
    fun bindCreatorsRemoteSource(creatorsRemoteSourceImpl: CreatorsRemoteSourceImpl): CreatorsRemoteSource

    @GameDetailsScope
    @Binds
    fun bindCreatorsLocalSource(creatorsLocalSourceImpl: CreatorsLocalSourceImpl): CreatorsLocalSource

    @GameDetailsScope
    @Binds
    fun bindGamesShortDataLocalSource(gamesShortDataLocalSourceImpl: GamesShortDataLocalSourceImpl): GamesShortDataLocalSource
}