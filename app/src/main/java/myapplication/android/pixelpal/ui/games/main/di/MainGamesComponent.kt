package myapplication.android.pixelpal.ui.games.main.di

import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.creators.CreatorsViewModel
import myapplication.android.pixelpal.ui.games.main.MainGamesFragment
import myapplication.android.pixelpal.ui.games.main.MainGamesViewModel
import javax.inject.Scope

@MainGamesScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        MainGamesModule::class,
        MainGamesLocalDIModule::class
    ]
)
interface MainGamesComponent {

    fun mainGamesViewModelFactory(): MainGamesViewModel.MainGamesViewModelAssistedFactory

    fun inject(fragment: MainGamesFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): MainGamesComponent
    }
}

@Scope
annotation class MainGamesScope