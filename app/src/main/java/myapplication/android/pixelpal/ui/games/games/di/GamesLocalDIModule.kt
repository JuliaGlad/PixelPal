package myapplication.android.pixelpal.ui.games.games.di

import dagger.Module
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.ui.games.games.mvi.GamesLocalDI

@Module
class GamesLocalDIModule {

    fun provideGamesLocalDI(
        gamesRepository: GamesRepository
    ): GamesLocalDI = GamesLocalDI(gamesRepository)

}