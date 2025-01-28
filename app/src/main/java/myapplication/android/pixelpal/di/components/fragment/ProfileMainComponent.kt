package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.genres.GenresRepository
import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.ui.games.main.MainGamesFragment
import myapplication.android.pixelpal.ui.games.main.MainGamesViewModel
import myapplication.android.pixelpal.ui.games.main.mvi.MainGamesLocalDI
import myapplication.android.pixelpal.ui.profile.main.ProfileMainFragment
import myapplication.android.pixelpal.ui.profile.main.mvi.ProfileMainLocalDI
import javax.inject.Scope

@ProfileMainScope
@Subcomponent(
    modules = [ProfileMainModule::class]
)
interface ProfileMainComponent {
    fun inject(fragment : ProfileMainFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): ProfileMainComponent
    }
}

@Module
class ProfileMainModule{
    @ProfileMainScope
    @Provides
    fun provideProfileMainLocalDI(
        userRepository: UserRepository
    ): ProfileMainLocalDI = ProfileMainLocalDI(userRepository)
}


@Scope
annotation class ProfileMainScope