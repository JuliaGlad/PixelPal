package myapplication.android.pixelpal.ui.all_games.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.all_games.AllGamesFragment
import javax.inject.Scope

@AllGamesScope
@Component(
    modules = [
        AllGamesModule::class,
        AllGamesLocalDIModule::class
    ],
    dependencies = [AppComponent::class]
)
interface AllGamesComponent {

    fun inject(fragment: AllGamesFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): AllGamesComponent
    }
}

@Scope
annotation class AllGamesScope