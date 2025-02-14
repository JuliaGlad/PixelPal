package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.favorite_games.FavoriteGamesRepository
import myapplication.android.pixelpal.ui.profile.favorite_games.FavoriteGamesFragment
import myapplication.android.pixelpal.ui.profile.favorite_games.mvi.FavoriteGamesLocalDI

@FavoriteGamesScope
@Subcomponent(modules = [CreatorDetailsModule::class])
interface FavoriteGamesComponent {

    fun inject(fragment: FavoriteGamesFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): FavoriteGamesComponent
    }

}

@FavoriteGamesScope
@Module
class FavoriteGamesModule{

    @FavoriteGamesScope
    @Provides
    fun provideFavoriteGamesLocalDi(
        favoriteGamesRepository: FavoriteGamesRepository
    ): FavoriteGamesLocalDI =
        FavoriteGamesLocalDI(favoriteGamesRepository)

}

annotation class FavoriteGamesScope