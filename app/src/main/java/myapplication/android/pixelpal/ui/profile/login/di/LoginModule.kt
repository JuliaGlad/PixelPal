package myapplication.android.pixelpal.ui.profile.login.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.data.repository.user.UserRepositoryImpl

@Module
interface LoginModule {

    @LoginScope
    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

}