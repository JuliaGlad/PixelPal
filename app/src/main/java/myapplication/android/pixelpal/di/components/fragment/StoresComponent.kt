package myapplication.android.pixelpal.di.components.fragment

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.di.components.AppComponent
import myapplication.android.pixelpal.ui.home.mvi.HomeLocalDI
import myapplication.android.pixelpal.ui.platforms.fragments.store.StoresFragment
import myapplication.android.pixelpal.ui.platforms.fragments.store.mvi.StoresLocalDI
import javax.inject.Scope

@StoresScope
@Subcomponent(
    modules = [StoresModule::class]
)
interface StoresComponent {
    fun inject(fragment : StoresFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): StoresComponent
    }
}

@Module
class StoresModule{
    @StoresScope
    @Provides
    fun provideStoresLocalDI(
        storesRepository: StoresRepository
    ): StoresLocalDI = StoresLocalDI(storesRepository)
}


@Scope
annotation class StoresScope