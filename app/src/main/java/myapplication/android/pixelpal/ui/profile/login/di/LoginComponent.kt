package myapplication.android.pixelpal.ui.profile.login.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.profile.login.LoginFragment
import javax.inject.Scope

@LoginScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        LoginModule::class,
        LoginLocalDIModule::class
    ]
)
interface LoginComponent {

    fun inject(fragment: LoginFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): LoginComponent
    }
}

@Scope
annotation class LoginScope