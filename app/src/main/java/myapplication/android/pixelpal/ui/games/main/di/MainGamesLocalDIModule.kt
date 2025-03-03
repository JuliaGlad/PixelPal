package myapplication.android.pixelpal.ui.games.main.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.genres.GenresRepository
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesLocalDI

@Module
class MainGamesLocalDIModule {

    @MainGamesScope
    @Provides
    fun provideMainGamesLocalDI(
        genresRepository: GenresRepository
    ): MainGamesLocalDI = MainGamesLocalDI(genresRepository)

}