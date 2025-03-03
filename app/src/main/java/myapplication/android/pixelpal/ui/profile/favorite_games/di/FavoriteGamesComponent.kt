package myapplication.android.pixelpal.ui.profile.favorite_games.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.profile.favorite_games.FavoriteGamesFragment
import javax.inject.Scope

@FavoriteGamesScope
@Component(
    modules = [FavoriteGamesModule::class],
    dependencies = [AppComponent::class]
)
interface FavoriteGamesComponent {

    fun inject(fragment: FavoriteGamesFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): FavoriteGamesComponent
    }
}

@Scope
annotation class FavoriteGamesScope