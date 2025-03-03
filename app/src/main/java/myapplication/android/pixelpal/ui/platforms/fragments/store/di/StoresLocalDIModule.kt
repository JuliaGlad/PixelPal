package myapplication.android.pixelpal.ui.platforms.fragments.store.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresLocalDI

@Module
class StoresLocalDIModule {

    @StoresScope
    @Provides
    fun provideStoresLocalDI(
        storesRepository: StoresRepository
    ): StoresLocalDI = StoresLocalDI(storesRepository)

}