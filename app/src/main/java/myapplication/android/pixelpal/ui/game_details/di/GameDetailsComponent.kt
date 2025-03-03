package myapplication.android.pixelpal.ui.game_details.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.game_details.GameDetailsFragment
import javax.inject.Scope

@GameDetailsScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        GameDetailsModule::class,
        GameDetailsLocalDIModule::class
    ]
)
interface GameDetailsComponent {

    fun inject(fragment: GameDetailsFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): GameDetailsComponent
    }
}

@Scope
annotation class GameDetailsScope