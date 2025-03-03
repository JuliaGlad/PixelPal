package myapplication.android.pixelpal.ui.publisher_details.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepositoryImpl
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepositoryImpl
import myapplication.android.pixelpal.data.source.games.GamesLocalSource
import myapplication.android.pixelpal.data.source.games.GamesLocalSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSource
import myapplication.android.pixelpal.data.source.games.GamesShortDataLocalSourceImpl
import myapplication.android.pixelpal.data.source.pulisher.PublisherLocalSource
import myapplication.android.pixelpal.data.source.pulisher.PublisherRemoteSource
import myapplication.android.pixelpal.data.source.pulisher.PublishersLocalSourceImpl
import myapplication.android.pixelpal.data.source.pulisher.PublishersRemoteSourceImpl

@Module
interface PublisherDetailsModule {

    @PublisherDetailsScope
    @Binds
    fun bindPublisherRepository(publishersRepositoryImpl: PublishersRepositoryImpl): PublishersRepository

    @PublisherDetailsScope
    @Binds
    fun bindPublisherRemoteSource(publishersRemoteSourceImpl: PublishersRemoteSourceImpl): PublisherRemoteSource

    @PublisherDetailsScope
    @Binds
    fun bindPublisherLocalSource(publishersLocalSourceImpl: PublishersLocalSourceImpl): PublisherLocalSource

    @PublisherDetailsScope
    @Binds
    fun bindGamesRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository

    @PublisherDetailsScope
    @Binds
    fun bindGamesRemoteSource(gamesRemoteSourceImpl: GamesRemoteSourceImpl): GamesRemoteSource

    @PublisherDetailsScope
    @Binds
    fun bindGamesLocalSource(gamesLocalSourceImpl: GamesLocalSourceImpl): GamesLocalSource

    @PublisherDetailsScope
    @Binds
    fun bindGamesShortDataLocalSource(gamesShortDataLocalSourceImpl: GamesShortDataLocalSourceImpl): GamesShortDataLocalSource

}