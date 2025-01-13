package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.StoreDetailsFragment
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.mvi.StoreDetailsLocalDI

@StoreDetailsScope
@Subcomponent(modules = [StoreDetailsModule::class])
interface StoreDetailsComponent {

    fun inject(fragment: StoreDetailsFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): StoreDetailsComponent
    }

}

@StoreDetailsScope
@Module
class StoreDetailsModule{

    @StoreDetailsScope
    @Provides
    fun provideStoreDetailsLocalDI(
        storesRepository: StoresRepository
    ): StoreDetailsLocalDI = StoreDetailsLocalDI(storesRepository)

}

annotation class StoreDetailsScope