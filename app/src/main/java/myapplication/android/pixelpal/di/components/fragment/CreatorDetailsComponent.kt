package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.ui.creator_details.CreatorDetailsFragment
import myapplication.android.pixelpal.ui.creator_details.mvi.CreatorDetailsLocalDI

@CreatorDetailsScope
@Subcomponent(modules = [CreatorDetailsModule::class])
interface CreatorDetailsComponent {

    fun inject(fragment: CreatorDetailsFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): CreatorDetailsComponent
    }

}

@CreatorDetailsScope
@Module
class CreatorDetailsModule{

    @CreatorDetailsScope
    @Provides
    fun provideCreatorDetailsLocalDi(
        creatorsRepository: CreatorsRepository,
        gamesRepository: GamesRepository
    ): CreatorDetailsLocalDI =
        CreatorDetailsLocalDI(creatorsRepository, gamesRepository)

}

annotation class CreatorDetailsScope