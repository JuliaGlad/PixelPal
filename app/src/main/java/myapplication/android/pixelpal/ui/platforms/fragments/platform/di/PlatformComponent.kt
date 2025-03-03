package myapplication.android.pixelpal.ui.platforms.fragments.platform.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.platforms.PlatformsFragment
import myapplication.android.pixelpal.ui.platforms.fragments.platform.PlatformDetailsFragment
import javax.inject.Scope

@PlatformScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        PlatformModule::class,
        PlatformLocalDIModule::class
    ]
)
interface PlatformComponent {

    fun inject(fragment: PlatformDetailsFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): PlatformComponent
    }
}

@Scope
annotation class PlatformScope