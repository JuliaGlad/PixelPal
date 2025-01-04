package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.ui.games.games.GamesFragment
import myapplication.android.pixelpal.ui.games.games.mvi.GamesLocalDI
import javax.inject.Scope

@GamesScope
@Subcomponent(
    modules = [GamesModule::class]
)
interface GamesComponent {
    fun inject(gamesFragment: GamesFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): GamesComponent
    }
}

@Module
class GamesModule{
    @GamesScope
    @Provides
    fun provideGamesLocalDI(
        gamesRepository: GamesRepository
    ): GamesLocalDI = GamesLocalDI(gamesRepository)
}


@Scope
annotation class GamesScope