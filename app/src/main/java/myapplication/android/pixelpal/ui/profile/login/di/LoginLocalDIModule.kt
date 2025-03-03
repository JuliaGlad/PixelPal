package myapplication.android.pixelpal.ui.profile.login.di

import dagger.Module
import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.ui.profile.login.mvi.LoginLocalDI

@Module
class LoginLocalDIModule {

    fun provideLoginLocalDI(
        userRepository: UserRepository
    ): LoginLocalDI = LoginLocalDI(userRepository)

}