package myapplication.android.pixelpal.ui.creators.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepositoryImpl
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepositoryImpl
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSource
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSourceImpl
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSource
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSourceImpl
import myapplication.android.pixelpal.data.source.pulisher.PublisherLocalSource
import myapplication.android.pixelpal.data.source.pulisher.PublisherRemoteSource
import myapplication.android.pixelpal.data.source.pulisher.PublishersLocalSourceImpl
import myapplication.android.pixelpal.data.source.pulisher.PublishersRemoteSourceImpl

@Module
interface CreatorsModule {

    @CreatorsScope
    @Binds
    fun bindPublisherRepository(publishersRepositoryImpl: PublishersRepositoryImpl): PublishersRepository

    @CreatorsScope
    @Binds
    fun bindPublisherRemoteSource(publishersRemoteSourceImpl: PublishersRemoteSourceImpl): PublisherRemoteSource

    @CreatorsScope
    @Binds
    fun bindPublisherLocalSource(publishersLocalSourceImpl: PublishersLocalSourceImpl): PublisherLocalSource

    @CreatorsScope
    @Binds
    fun bindCreatorsRepository(creatorsRepositoryImpl: CreatorsRepositoryImpl): CreatorsRepository

    @CreatorsScope
    @Binds
    fun bindCreatorsRemoteSource(creatorsRemoteSourceImpl: CreatorsRemoteSourceImpl): CreatorsRemoteSource

    @CreatorsScope
    @Binds
    fun bindCreatorsLocalSource(creatorsLocalSourceImpl: CreatorsLocalSourceImpl): CreatorsLocalSource
}