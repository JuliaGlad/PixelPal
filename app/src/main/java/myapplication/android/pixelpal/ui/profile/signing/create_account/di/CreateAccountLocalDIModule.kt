package myapplication.android.pixelpal.ui.profile.signing.create_account.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.ui.profile.signing.create_account.mvi.CreateAccountLocalDI

@Module
class CreateAccountLocalDIModule {

    @CreateAccountScope
    @Provides
    fun provideCreateAccountLocalDI(
        userRepository: UserRepository
    ): CreateAccountLocalDI = CreateAccountLocalDI(userRepository)

}