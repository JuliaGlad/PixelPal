package myapplication.android.pixelpal.ui.profile.signing.create_account.di

import dagger.Binds
import dagger.Module
import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.data.repository.user.UserRepositoryImpl

@Module
interface CreateAccountModule {

    @CreateAccountScope
    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

}