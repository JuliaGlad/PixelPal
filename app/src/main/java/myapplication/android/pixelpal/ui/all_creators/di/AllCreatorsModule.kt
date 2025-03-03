package myapplication.android.pixelpal.ui.all_creators.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepositoryImpl
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSource
import myapplication.android.pixelpal.data.source.creators.CreatorsLocalSourceImpl
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSource
import myapplication.android.pixelpal.data.source.creators.CreatorsRemoteSourceImpl

@Module
interface AllCreatorsModule {

    @AllCreatorsScope
    @Binds
    fun bindCreatorsRepository(creatorsRepositoryImpl: CreatorsRepositoryImpl): CreatorsRepository

    @AllCreatorsScope
    @Binds
    fun bindCreatorsRemoteSource(creatorsRemoteSourceImpl: CreatorsRemoteSourceImpl): CreatorsRemoteSource


    @AllCreatorsScope
    @Binds
    fun bindCreatorsLocalSource(creatorsLocalSourceImpl: CreatorsLocalSourceImpl): CreatorsLocalSource
}