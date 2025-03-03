package myapplication.android.pixelpal.ui.home.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.home.HomeFragment
import javax.inject.Scope

@HomeScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        HomeModule::class,
        HomeLocalDIModule::class
    ]
)
interface HomeComponent {

    fun inject(fragment: HomeFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): HomeComponent
    }
}

@Scope
annotation class HomeScope