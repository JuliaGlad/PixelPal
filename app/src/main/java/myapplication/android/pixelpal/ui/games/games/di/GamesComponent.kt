package myapplication.android.pixelpal.ui.games.games.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.games.games.GamesFragment
import javax.inject.Scope

@GamesScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        GamesLocalDIModule::class,
        GamesModule::class
    ]
)
interface GamesComponent {

    fun inject(fragment: GamesFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): GamesComponent
    }
}

@Scope
annotation class GamesScope