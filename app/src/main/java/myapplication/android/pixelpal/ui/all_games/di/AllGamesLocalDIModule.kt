package myapplication.android.pixelpal.ui.all_games.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.ui.all_games.mvi.AllGamesLocalDI

@Module
class AllGamesLocalDIModule {

    @AllGamesScope
    @Provides
    fun provideAllGamesLocalDI(
        gamesRepository: GamesRepository
    ): AllGamesLocalDI = AllGamesLocalDI(gamesRepository)

}