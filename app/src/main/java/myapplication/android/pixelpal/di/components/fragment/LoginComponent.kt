package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.ui.profile.login.LoginFragment
import myapplication.android.pixelpal.ui.profile.login.mvi.LoginLocalDI
import javax.inject.Scope

@LoginScope
@Subcomponent(
    modules = [LoginModule::class]
)
interface  LoginComponent {
    fun inject(loginFragment: LoginFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): LoginComponent
    }
}

@Scope
annotation class LoginScope

@Module
class LoginModule{
    @LoginScope
    @Provides
    fun provideLoginLocalDI(
        userRepository: UserRepository
    ): LoginLocalDI = LoginLocalDI(userRepository)
}

