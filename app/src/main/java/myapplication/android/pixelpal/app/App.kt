package myapplication.android.pixelpal.app

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.github.terrakok.cicerone.Cicerone
import myapplication.android.pixelpal.data.database.LocalDataBase
import myapplication.android.pixelpal.di.components.AppComponent
import myapplication.android.pixelpal.di.components.DaggerAppComponent

internal class App : Application() {

    private val cicerone = Cicerone.create()
    val database: LocalDataBase by lazy { createDatabase() }
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        appComponent = createComponent()
        app = this
    }

    private fun createDatabase() =
        databaseBuilder(this, LocalDataBase::class.java, "database")
        .allowMainThreadQueries()
        .build()

    private fun createComponent(): AppComponent {
        return DaggerAppComponent.factory().create(this.applicationContext)
    }

    companion object {
        internal lateinit var app: App
            private set
        internal lateinit var appComponent: AppComponent
    }

}