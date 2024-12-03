package myapplication.android.pixelpal.di.components.main

import dagger.Subcomponent
import myapplication.android.pixelpal.ui.main.MainActivity

@MainActivityScope
@Subcomponent
interface MainActivityComponent {

    fun inject(activity: MainActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create(): MainActivityComponent
    }
}

annotation class MainActivityScope