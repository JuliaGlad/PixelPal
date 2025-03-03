package myapplication.android.pixelpal.ui.creator_details.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepositoryImpl
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepositoryImpl
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSource
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSourceImpl
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSource
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesLocalSource
import myapplication.android.pixelpal.data.source.games.GamesLocalSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSource
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSourceImpl
import myapplication.android.pixelpal.ui.all_games.di.AllGamesScope

@Module
interface CreatorsDetailsModule {

    @CreatorsDetailsScope
    @Binds
    fun bindCreatorsRepository(creatorsRepositoryImpl: CreatorsRepositoryImpl): CreatorsRepository

    @CreatorsDetailsScope
    @Binds
    fun bindCreatorsRemoteSource(creatorsRemoteSourceImpl: CreatorsRemoteSourceImpl): CreatorsRemoteSource

    @CreatorsDetailsScope
    @Binds
    fun bindCreatorsLocalSource(creatorsLocalSourceImpl: CreatorsLocalSourceImpl): CreatorsLocalSource

    @CreatorsDetailsScope
    @Binds
    fun bindGamesRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository

    @CreatorsDetailsScope
    @Binds
    fun bindGamesRemoteSource(gamesRemoteSourceImpl: GamesRemoteSourceImpl): GamesRemoteSource

    @CreatorsDetailsScope
    @Binds
    fun bindGamesLocalSource(gamesLocalSource: GamesLocalSourceImpl): GamesLocalSource

    @CreatorsDetailsScope
    @Binds
    fun bindGamesShortDataLocalSource(gamesShortDataLocalSourceImpl: GamesShortDataLocalSourceImpl): GamesShortDataLocalSource
}