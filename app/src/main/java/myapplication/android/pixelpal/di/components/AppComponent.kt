package myapplication.android.pixelpal.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import myapplication.android.pixelpal.di.LocalSourceModule
import myapplication.android.pixelpal.di.RemoteSourceModule
import myapplication.android.pixelpal.di.RepositoryModule
import myapplication.android.pixelpal.di.RetrofitModule
import myapplication.android.pixelpal.di.components.fragment.AllGamesComponent
import myapplication.android.pixelpal.di.components.fragment.CreatorsComponent
import myapplication.android.pixelpal.di.components.fragment.GameDetailsComponent
import myapplication.android.pixelpal.di.components.fragment.GamesComponent
import myapplication.android.pixelpal.di.components.fragment.HomeComponent
import myapplication.android.pixelpal.di.components.fragment.MainGamesComponent
import myapplication.android.pixelpal.di.components.fragment.PlatformComponent
import myapplication.android.pixelpal.di.components.fragment.StoresComponent
import myapplication.android.pixelpal.di.components.main.GameDetailsActivityComponent
import myapplication.android.pixelpal.di.components.main.MainActivityComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        LocalSourceModule::class,
        RemoteSourceModule::class,
        RepositoryModule::class,
        RetrofitModule::class
    ]
)
interface AppComponent {

    fun gameDetailsActivityComponent(): GameDetailsActivityComponent.Factory

    fun gameDetailsComponent(): GameDetailsComponent.Factory

    fun mainActivityComponent(): MainActivityComponent.Factory

    fun creatorsComponent(): CreatorsComponent.Factory

    fun gamesComponent(): GamesComponent.Factory

    fun homeComponent(): HomeComponent.Factory

    fun mainGamesComponent(): MainGamesComponent.Factory

    fun platformComponent(): PlatformComponent.Factory

    fun storesComponent(): StoresComponent.Factory

    fun allGamesComponent(): AllGamesComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

@Module(
    subcomponents = [
        AllGamesComponent::class,
        GameDetailsActivityComponent::class,
        MainActivityComponent::class,
        GameDetailsComponent::class,
        CreatorsComponent::class,
        GamesComponent::class,
        HomeComponent::class,
        MainGamesComponent::class,
        PlatformComponent::class,
        StoresComponent::class
    ]
)
class AppModule