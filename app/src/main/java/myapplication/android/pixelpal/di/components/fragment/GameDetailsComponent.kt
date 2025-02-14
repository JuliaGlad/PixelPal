package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.favorite_games.FavoriteGamesRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.ui.game_details.GameDetailsFragment
import myapplication.android.pixelpal.ui.game_details.mvi.GameDetailsLocalDI
import javax.inject.Scope

@GameDetailsScope
@Subcomponent(
    modules = [GameDetailsModule::class]
)
interface GameDetailsComponent {
    fun inject(gamesDetailsFragment: GameDetailsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): GameDetailsComponent
    }
}

@Module
class GameDetailsModule {
    @GameDetailsScope
    @Provides
    fun provideGameDetailsLocalDI(
        creatorsRepository: CreatorsRepository,
        gamesRepository: GamesRepository,
        storesRepository: StoresRepository,
        favoriteGamesRepository: FavoriteGamesRepository
    ): GameDetailsLocalDI =
        GameDetailsLocalDI(
            creatorsRepository,
            gamesRepository,
            storesRepository,
            favoriteGamesRepository
        )
}

@Scope
annotation class GameDetailsScope