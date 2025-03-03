package myapplication.android.pixelpal.ui.profile.main.di

import dagger.Module
import dagger.Provides
import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.ui.profile.main.mvi.ProfileMainLocalDI

@Module
class ProfileMainLocalDIModule {

    @ProfileMainScope
    @Provides
    fun provideProfileMainLocalDI(
        userRepository: UserRepository
    ): ProfileMainLocalDI = ProfileMainLocalDI(userRepository)

}