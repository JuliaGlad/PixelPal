package myapplication.android.pixelpal.ui.creators.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.ui.creators.mvi.CreatorsLocalDI

@Module
class CreatorsLocalDIModule {

    @CreatorsScope
    @Provides
    fun provideCreatorsLocalDI(
        creatorsRepository: CreatorsRepository,
        publishersRepository: PublishersRepository
    ): CreatorsLocalDI = CreatorsLocalDI(
        creatorsRepository,
        publishersRepository
    )
}