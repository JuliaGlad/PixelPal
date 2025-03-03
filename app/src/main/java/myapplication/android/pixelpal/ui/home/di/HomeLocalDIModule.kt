package myapplication.android.pixelpal.ui.home.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.ui.home.mvi.HomeLocalDI

@Module
class HomeLocalDIModule {

    @HomeScope
    @Provides
    fun provideHomeLocalDI(
        gamesRepository: GamesRepository
    ): HomeLocalDI = HomeLocalDI(gamesRepository)

}