package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.ui.home.HomeFragment
import myapplication.android.pixelpal.ui.home.mvi.HomeLocalDI
import javax.inject.Scope

@HomeScope
@Subcomponent(
    modules = [HomeModule::class]
)
interface  HomeComponent {
    fun inject(homeFragment: HomeFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): HomeComponent
    }
}

@Scope
annotation class HomeScope

@Module
class HomeModule{
    @HomeScope
    @Provides
    fun provideHomeLocalDI(
        gamesRepository: GamesRepository
    ): HomeLocalDI = HomeLocalDI(gamesRepository)
}

