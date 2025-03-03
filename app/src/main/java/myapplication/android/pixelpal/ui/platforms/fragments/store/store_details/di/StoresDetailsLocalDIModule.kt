package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi.StoreDetailsLocalDI

@Module
class StoresDetailsLocalDIModule {

    @StoreDetailsScope
    @Provides
    fun provideStoresDetailsLocalDI(
        storesRepository: StoresRepository,
        gamesRepository: GamesRepository
    ): StoreDetailsLocalDI = StoreDetailsLocalDI(
        storesRepository,
        gamesRepository
    )

}