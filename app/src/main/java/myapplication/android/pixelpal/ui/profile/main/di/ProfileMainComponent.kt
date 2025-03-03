package myapplication.android.pixelpal.ui.profile.main.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.profile.main.ProfileMainFragment
import javax.inject.Scope

@ProfileMainScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        ProfileMainModule::class,
        ProfileMainLocalDIModule::class
    ]
)
interface ProfileMainComponent {

    fun inject(fragment: ProfileMainFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): ProfileMainComponent
    }
}

@Scope
annotation class ProfileMainScope