package myapplication.android.pixelpal.ui.profile.signing.create_account.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.profile.signing.create_account.CreateAccountMainFragment
import javax.inject.Scope

@CreateAccountScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CreateAccountLocalDIModule::class,
        CreateAccountModule::class
    ]
)
interface CreateAccountComponent {

    fun inject(fragment: CreateAccountMainFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): CreateAccountComponent
    }
}

@Scope
annotation class CreateAccountScope