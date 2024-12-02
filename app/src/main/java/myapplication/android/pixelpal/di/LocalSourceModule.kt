package myapplication.android.pixelpal.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSource
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSourceImpl
import myapplication.android.pixelpal.data.source.games.GamesLocalSource
import myapplication.android.pixelpal.data.source.games.GamesLocalSourceImpl
import myapplication.android.pixelpal.data.source.genres.GenresLocalSource
import myapplication.android.pixelpal.data.source.genres.GenresLocalSourceImpl
import myapplication.android.pixelpal.data.source.platform.PlatformLocalSource
import myapplication.android.pixelpal.data.source.platform.PlatformLocalSourceImpl
import myapplication.android.pixelpal.data.source.pulisher.PublisherLocalSource
import myapplication.android.pixelpal.data.source.pulisher.PublishersLocalSourceImpl
import myapplication.android.pixelpal.data.source.stores.StoresLocalSource
import myapplication.android.pixelpal.data.source.stores.StoresLocalSourceImpl
import javax.inject.Singleton

@Module
interface LocalSourceModule {
    @Singleton
    @Binds
    fun bindLocalSourceCreators(localSourceImpl: CreatorsLocalSourceImpl): CreatorsLocalSource

    @Singleton
    @Binds
    fun bindLocalSourceGames(localSourceImpl: GamesLocalSourceImpl): GamesLocalSource

    @Singleton
    @Binds
    fun bindLocalSourceGenres(localSourceImpl: GenresLocalSourceImpl): GenresLocalSource

    @Singleton
    @Binds
    fun bindLocalSourcePlatform(localSourceImpl: PlatformLocalSourceImpl): PlatformLocalSource

    @Singleton
    @Binds
    fun bindLocalSourcePublisher(localSourceImpl: PublishersLocalSourceImpl): PublisherLocalSource

    @Singleton
    @Binds
    fun bindLocalSourceStore(localSourceImpl: StoresLocalSourceImpl): StoresLocalSource
}