package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.StoreDetailsFragment
import javax.inject.Scope

@StoreDetailsScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        StoresDetailsModule::class,
        StoresDetailsLocalDIModule::class
    ]
)
interface StoresDetailsComponent {

    fun inject(fragment: StoreDetailsFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): StoresDetailsComponent
    }
}

@Scope
annotation class StoreDetailsScope