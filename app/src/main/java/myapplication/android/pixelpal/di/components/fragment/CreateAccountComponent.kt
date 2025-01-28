package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.ui.profile.signing.create_account.CreateAccountMainFragment
import myapplication.android.pixelpal.ui.profile.signing.create_account.mvi.CreateAccountLocalDI
import javax.inject.Scope

@CreateAccountScope
@Subcomponent(
    modules = [CreateAccountModule::class]
)
interface  CreateAccountComponent {
    fun inject(createAccountFragment: CreateAccountMainFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): CreateAccountComponent
    }
}

@Scope
annotation class CreateAccountScope

@Module
class CreateAccountModule{
    @CreateAccountScope
    @Provides
    fun provideCreateAccountLocalDI(
        userRepository: UserRepository
    ): CreateAccountLocalDI = CreateAccountLocalDI(userRepository)
}

