package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.PlatformFullDetailsFragment
import javax.inject.Scope

@PlatformDetailsScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        PlatformDetailsModule::class,
        PlatformDetailsLocalDIModule::class
    ]
)
interface PlatformDetailsComponent {

    fun inject(fragment: PlatformFullDetailsFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): PlatformDetailsComponent
    }
}

@Scope
annotation class PlatformDetailsScope