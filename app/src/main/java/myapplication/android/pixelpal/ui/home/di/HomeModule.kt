package myapplication.android.pixelpal.ui.home.di

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
interface HomeModule {

    @HomeScope
    @Binds
    fun bindGamesRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository

    @HomeScope
    @Binds
    fun bindGamesRemoteSource(gamesRemoteSourceImpl: GamesRemoteSourceImpl): GamesRemoteSource

    @HomeScope
    @Binds
    fun bindGamesLocalSource(gamesLocalSourceImpl: GamesLocalSourceImpl): GamesLocalSource

    @HomeScope
    @Binds
    fun bindGamesShortDataLocalSource(gamesShortDataLocalSourceImpl: GamesShortDataLocalSourceImpl): GamesShortDataLocalSource

}