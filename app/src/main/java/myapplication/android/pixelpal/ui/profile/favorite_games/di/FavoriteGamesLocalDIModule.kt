package myapplication.android.pixelpal.ui.profile.favorite_games.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.favorite_games.FavoriteGamesRepository
import myapplication.android.pixelpal.ui.profile.favorite_games.mvi.FavoriteGamesLocalDI

@Module
class FavoriteGamesLocalDIModule {

    @FavoriteGamesScope
    @Provides
    fun provideFavoriteGamesLocalDI(
        favoriteGamesRepository: FavoriteGamesRepository
    ): FavoriteGamesLocalDI = FavoriteGamesLocalDI(favoriteGamesRepository)

}