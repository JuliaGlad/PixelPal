package myapplication.android.pixelpal.ui.game_details.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.favorite_games.FavoriteGamesRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.stores.StoresRepository
import myapplication.android.pixelpal.ui.game_details.mvi.GameDetailsLocalDI

@Module
class GameDetailsLocalDIModule {

    @GameDetailsScope
    @Provides
    fun provideGameDetailsLocalDI(
        creatorsRepository: CreatorsRepository,
        gamesRepository: GamesRepository,
        storesRepository: StoresRepository,
        favoriteGamesRepository: FavoriteGamesRepository
    ): GameDetailsLocalDI = GameDetailsLocalDI(
        creatorsRepository,
        gamesRepository,
        storesRepository,
        favoriteGamesRepository
    )

}