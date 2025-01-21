package myapplication.android.pixelpal.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import myapplication.android.pixelpal.di.LocalSourceModule
import myapplication.android.pixelpal.di.RemoteSourceModule
import myapplication.android.pixelpal.di.RepositoryModule
import myapplication.android.pixelpal.di.RetrofitModule
import myapplication.android.pixelpal.di.components.activity.AllCreatorsActivityComponent
import myapplication.android.pixelpal.di.components.activity.CreatorDetailsActivityComponent
import myapplication.android.pixelpal.di.components.activity.GameDetailsActivityComponent
import myapplication.android.pixelpal.di.components.activity.MainActivityComponent
import myapplication.android.pixelpal.di.components.activity.PlatformDetailsActivityComponent
import myapplication.android.pixelpal.di.components.activity.PublisherDetailsActivityComponent
import myapplication.android.pixelpal.di.components.activity.StoreDetailsActivityComponent
import myapplication.android.pixelpal.di.components.fragment.AllCreatorsComponent
import myapplication.android.pixelpal.di.components.fragment.AllGamesComponent
import myapplication.android.pixelpal.di.components.fragment.CreatorDetailsComponent
import myapplication.android.pixelpal.di.components.fragment.CreatorsComponent
import myapplication.android.pixelpal.di.components.fragment.GameDetailsComponent
import myapplication.android.pixelpal.di.components.fragment.GamesComponent
import myapplication.android.pixelpal.di.components.fragment.HomeComponent
import myapplication.android.pixelpal.di.components.fragment.MainGamesComponent
import myapplication.android.pixelpal.di.components.fragment.PlatformComponent
import myapplication.android.pixelpal.di.components.fragment.PlatformDetailsComponent
import myapplication.android.pixelpal.di.components.fragment.PublisherDetailsComponent
import myapplication.android.pixelpal.di.components.fragment.StoreDetailsComponent
import myapplication.android.pixelpal.di.components.fragment.StoresComponent
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

    fun allCreatorsComponent(): AllCreatorsComponent.Factory

    fun allCreatorsActivityComponent(): AllCreatorsActivityComponent.Factory

    fun publisherDetailsActivityComponent(): PublisherDetailsActivityComponent.Factory

    fun storeDetailsActivityComponent(): StoreDetailsActivityComponent.Factory

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

    fun creatorDetailsComponent(): CreatorDetailsComponent.Factory

    fun creatorDetailsActivityComponent(): CreatorDetailsActivityComponent.Factory

    fun storeDetailsComponent(): StoreDetailsComponent.Factory

    fun platformDetailsComponent(): PlatformDetailsComponent.Factory

    fun publisherDetailsComponent(): PublisherDetailsComponent.Factory

    fun platformDetailsActivityComponent(): PlatformDetailsActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

@Module(
    subcomponents = [
        AllCreatorsActivityComponent::class,
        AllCreatorsComponent::class,
        PublisherDetailsActivityComponent::class,
        PublisherDetailsComponent::class,
        PlatformDetailsActivityComponent::class,
        PlatformDetailsComponent::class,
        StoreDetailsComponent::class,
        StoreDetailsActivityComponent::class,
        CreatorDetailsActivityComponent::class,
        CreatorDetailsComponent::class,
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