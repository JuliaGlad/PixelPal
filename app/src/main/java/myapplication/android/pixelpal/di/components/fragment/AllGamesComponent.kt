package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.ui.all_games.AllGamesFragment
import myapplication.android.pixelpal.ui.all_games.mvi.AllGamesLocalDI

@AllGamesScope
@Subcomponent(modules = [AllGamesModule::class])
interface AllGamesComponent {
    fun inject(allGamesFragment: AllGamesFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): AllGamesComponent
    }
}

@AllGamesScope
@Module
class AllGamesModule{

    @AllGamesScope
    @Provides
    fun provideTopGamesLocalDI(gamesRepository: GamesRepository): AllGamesLocalDI =
        AllGamesLocalDI(gamesRepository)

}

annotation class AllGamesScope