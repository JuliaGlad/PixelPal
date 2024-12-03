package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.genres.GenresRepository
import myapplication.android.pixelpal.ui.creators.CreatorsFragment
import myapplication.android.pixelpal.ui.creators.CreatorsViewModel
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesLocalDI
import javax.inject.Scope

@CreatorsScope
@Subcomponent(
    modules = [CreatorsModule::class]
)
interface  CreatorsComponent {

    fun creatorsViewModelFactory(): CreatorsViewModel.CreatorsViewModelAssistedFactory

    fun inject(fragment : CreatorsFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): CreatorsComponent
    }
}

@Module
class CreatorsModule{

    @MainGamesScope
    @Provides
    fun provideMainGamesLocalDI(
        genresRepository: GenresRepository
    ): MainGamesLocalDI = MainGamesLocalDI(genresRepository)
}

@Scope
annotation class CreatorsScope