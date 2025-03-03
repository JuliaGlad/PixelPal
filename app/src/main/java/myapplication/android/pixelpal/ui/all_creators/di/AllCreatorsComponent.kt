package myapplication.android.pixelpal.ui.all_creators.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.all_creators.AllCreatorsFragment
import javax.inject.Scope

@AllCreatorsScope
@Component(
    modules = [
        AllCreatorsModule::class,
        AllCreatorsLocalDIModule::class
    ],
    dependencies = [AppComponent::class]
)
interface AllCreatorsComponent {

    fun inject(fragment: AllCreatorsFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): AllCreatorsComponent
    }
}

@Scope
annotation class AllCreatorsScope