package myapplication.android.pixelpal.ui.platforms.fragments.store.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.data.repository.stores.StoresRepositoryImpl
import myapplication.android.pixelpal.data.source.stores.StoresLocalSource
import myapplication.android.pixelpal.data.source.stores.StoresLocalSourceImpl
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSource
import myapplication.android.pixelpal.data.source.stores.StoresRemoteSourceImpl
import myapplication.android.pixelpal.ui.game_details.di.GameDetailsScope

@Module
interface StoresModule {

    @StoresScope
    @Binds
    fun bindStoresRepository(storesRepositoryImpl: StoresRepositoryImpl): StoresRepository

    @StoresScope
    @Binds
    fun bindStoresLocalSource(storesLocalSourceImpl: StoresLocalSourceImpl): StoresLocalSource

    @StoresScope
    @Binds
    fun bindStoresRemoteSource(storeRemoteSourceImpl: StoresRemoteSourceImpl): StoresRemoteSource

}