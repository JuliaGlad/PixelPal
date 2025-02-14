package myapplication.android.pixelpal.di.components.activity

import dagger.Subcomponent
import myapplication.android.pixelpal.ui.profile.favorite_games.FavoriteGamesActivity

@FavoriteGamesActivityScope
@Subcomponent
interface FavoriteGamesActivityComponent {
    fun inject(activity: FavoriteGamesActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): FavoriteGamesActivityComponent
    }
}

annotation class FavoriteGamesActivityScope