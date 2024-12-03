package myapplication.android.pixelpal.app

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import myapplication.android.pixelpal.di.components.AppComponent
import myapplication.android.pixelpal.di.components.DaggerAppComponent

internal class App : Application() {

    lateinit var appComponent: AppComponent

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        appComponent = createComponent()
        INSTANCE = this
    }

    private fun createComponent(): AppComponent {
        return DaggerAppComponent.factory().create(this.applicationContext)
    }

    companion object {
        internal lateinit var INSTANCE: App
            private set
    }

}