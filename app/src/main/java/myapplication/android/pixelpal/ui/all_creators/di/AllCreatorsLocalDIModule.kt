package myapplication.android.pixelpal.ui.all_creators.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.ui.all_creators.mvi.AllCreatorsLocalDI

@Module
class AllCreatorsLocalDIModule {

    @AllCreatorsScope
    @Provides
    fun provideAllCreatorsLocalDI(
        creatorsRepository: CreatorsRepository
    ): AllCreatorsLocalDI = AllCreatorsLocalDI(creatorsRepository)
}