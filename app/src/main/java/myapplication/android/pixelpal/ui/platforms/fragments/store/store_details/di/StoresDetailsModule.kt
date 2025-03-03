package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepositoryImpl
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.data.repository.stores.StoresRepositoryImpl
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
import myapplication.android.pixelpal.ui.game_details.di.GameDetailsScope

@Module
interface StoresDetailsModule {

    @StoreDetailsScope
    @Binds
    fun bindStoresRepository(storesRepositoryImpl: StoresRepositoryImpl): StoresRepository

    @StoreDetailsScope
    @Binds
    fun bindStoresLocalSource(storesLocalSourceImpl: StoresLocalSourceImpl): StoresLocalSource

    @StoreDetailsScope
    @Binds
    fun bindStoresRemoteSource(storeRemoteSourceImpl: StoresRemoteSourceImpl): StoresRemoteSource

    @StoreDetailsScope
    @Binds
    fun bindGamesRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository

    @StoreDetailsScope
    @Binds
    fun bindGamesRemoteSource(gamesRemoteSourceImpl: GamesRemoteSourceImpl): GamesRemoteSource

    @StoreDetailsScope
    @Binds
    fun bindGamesLocalSource(gamesLocalSourceImpl: GamesLocalSourceImpl): GamesLocalSource

    @StoreDetailsScope
    @Binds
    fun bindGamesShortDataLocalSource(gamesShortDataLocalSourceImpl: GamesShortDataLocalSourceImpl): GamesShortDataLocalSource
}