package myapplication.android.pixelpal.ui.creator_details.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsLocalDI

@Module
class CreatorsDetailsLocalDIModule {

    @CreatorsDetailsScope
    @Provides
    fun provideCreatorsDetailsLocalDI(
        gamesRepository: GamesRepository,
        creatorsRepository: CreatorsRepository
    ): CreatorDetailsLocalDI = CreatorDetailsLocalDI(
        creatorsRepository, gamesRepository
    )

}