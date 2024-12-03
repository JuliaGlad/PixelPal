package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.genres.GenresRepository
import myapplication.android.pixelpal.ui.games.main.MainGamesFragment
import myapplication.android.pixelpal.ui.games.main.MainGamesViewModel
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesLocalDI
import javax.inject.Scope

@MainGamesScope
@Subcomponent(
    modules = [MainGamesModule::class]
)
interface MainGamesComponent {

    fun mainGamesViewModelFactory(): MainGamesViewModel.MainGamesViewModelAssistedFactory

    fun inject(gamesFragment : MainGamesFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): MainGamesComponent
    }
}

@Module
class MainGamesModule{
    @MainGamesScope
    @Provides
    fun provideMainGamesLocalDI(
        genresRepository: GenresRepository
    ): MainGamesLocalDI = MainGamesLocalDI(genresRepository)
}


@Scope
annotation class MainGamesScope