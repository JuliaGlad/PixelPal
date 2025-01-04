package myapplication.android.pixelpal.di.components.main

import dagger.Subcomponent
import myapplication.android.pixelpal.ui.game_details.GameDetailsActivity
import myapplication.android.pixelpal.ui.game_details.mvi.GameDetailsActor
import javax.inject.Scope

@GameDetailsActivityScope
@Subcomponent
interface GameDetailsActivityComponent {
    fun inject(activity: GameDetailsActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create(): GameDetailsActivityComponent
    }
}

@Scope
annotation class GameDetailsActivityScope