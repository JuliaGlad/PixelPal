package myapplication.android.pixelpal.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import myapplication.android.pixelpal.ui.creators.CreatorsFragment
import myapplication.android.pixelpal.ui.creators.CreatorsViewModel
import myapplication.android.pixelpal.ui.games.games.GamesFragment
import myapplication.android.pixelpal.ui.games.main.MainGamesFragment
import myapplication.android.pixelpal.ui.games.main.MainGamesViewModel
import myapplication.android.pixelpal.ui.home.HomeFragment
import myapplication.android.pixelpal.ui.main.MainActivity
import myapplication.android.pixelpal.ui.platforms.fragments.platform.PlatformDetailsFragment
import myapplication.android.pixelpal.ui.platforms.fragments.store.StoresFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        LocalSourceModule::class,
        RemoteSourceModule::class,
        RepositoryModule::class,
        RetrofitModule::class,
        LocalDIModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: HomeFragment)
    fun inject(fragment: MainGamesFragment)
    fun inject(fragment: GamesFragment)
    fun inject(fragment: StoresFragment)
    fun inject(fragment: PlatformDetailsFragment)
    fun inject(fragment: CreatorsFragment)
    fun inject(activity: MainActivity)

    fun creatorsViewModelFactory(): CreatorsViewModel.CreatorsViewModelAssistedFactory

    fun mainGamesViewModelFactory(): MainGamesViewModel.MainGamesViewModelAssistedFactory
}