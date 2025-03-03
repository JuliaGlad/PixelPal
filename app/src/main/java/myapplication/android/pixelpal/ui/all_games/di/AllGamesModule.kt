package myapplication.android.pixelpal.ui.all_games.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepositoryImpl
import myapplication.android.pixelpal.data.source.games.GamesLocalSource
import myapplication.android.pixelpal.data.source.games.GamesLocalSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSource
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSourceImpl

@Module
interface AllGamesModule {

    @AllGamesScope
    @Binds
    fun bindGamesRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository

    @AllGamesScope
    @Binds
    fun bindGamesRemoteSource(gamesRemoteSourceImpl: GamesRemoteSourceImpl): GamesRemoteSource

    @AllGamesScope
    @Binds
    fun bindGamesLocalSource(gamesLocalSource: GamesLocalSourceImpl): GamesLocalSource

    @AllGamesScope
    @Binds
    fun bindGamesShortDataLocalSource(gamesShortDataLocalSourceImpl: GamesShortDataLocalSourceImpl): GamesShortDataLocalSource

}