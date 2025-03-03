package myapplication.android.pixelpal.ui.profile.favorite_games.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.favorite_games.FavoriteGamesRepository
import myapplication.android.pixelpal.data.repository.favorite_games.FavoriteGamesRepositoryImpl
import myapplication.android.pixelpal.data.source.favorite_games.FavoriteGamesLocalSource
import myapplication.android.pixelpal.data.source.favorite_games.FavoriteGamesLocalSourceImpl
import myapplication.android.pixelpal.data.source.favorite_games.FavoriteGamesRemoteSource
import myapplication.android.pixelpal.data.source.favorite_games.FavoriteGamesRemoteSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSourceImpl

@Module
interface FavoriteGamesModule {

    @FavoriteGamesScope
    @Binds
    fun bindFavoriteGamesRepository(favoriteGamesRepositoryImpl: FavoriteGamesRepositoryImpl): FavoriteGamesRepository

    @FavoriteGamesScope
    @Binds
    fun bindFavoriteGamesRemoteSource(favoriteGamesRemoteSourceImpl: FavoriteGamesRemoteSourceImpl): FavoriteGamesRemoteSource

    @FavoriteGamesScope
    @Binds
    fun bindFavoriteGamesLocalSource(favoriteGamesLocalSourceImpl: FavoriteGamesLocalSourceImpl): FavoriteGamesLocalSource

    @FavoriteGamesScope
    @Binds
    fun bindGamesRemoteSource(gamesRemoteSourceImpl: GamesRemoteSourceImpl): GamesRemoteSource

}