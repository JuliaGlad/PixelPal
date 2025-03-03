package myapplication.android.pixelpal.ui.publisher_details.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.ui.publisher_details.mvi.PublisherDetailsLocalDI

@Module
class PublisherDetailsLocalDIModule {

    @PublisherDetailsScope
    @Provides
    fun providePublisherDetailsLocalDI(
        publishersRepository: PublishersRepository,
        gamesRepository: GamesRepository
    ): PublisherDetailsLocalDI = PublisherDetailsLocalDI(
        publishersRepository, gamesRepository
    )

}