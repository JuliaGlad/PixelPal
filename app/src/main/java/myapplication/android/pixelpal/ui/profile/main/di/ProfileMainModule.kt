package myapplication.android.pixelpal.ui.profile.main.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.data.repository.user.UserRepositoryImpl

@Module
interface ProfileMainModule {

    @ProfileMainScope
    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

}