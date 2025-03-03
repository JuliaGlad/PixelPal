package myapplication.android.pixelpal.ui.platforms.fragments.store.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.platforms.fragments.store.StoresFragment
import javax.inject.Scope

@StoresScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        StoresModule::class,
        StoresLocalDIModule::class
    ]
)
interface StoresComponent {

    fun inject(fragment: StoresFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): StoresComponent
    }
}

@Scope
annotation class StoresScope