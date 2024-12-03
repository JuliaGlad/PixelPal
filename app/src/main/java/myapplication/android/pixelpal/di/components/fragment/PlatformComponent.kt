package myapplication.android.pixelpal.di.components.fragment

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.games.GamesRepository
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.di.components.AppComponent
import myapplication.android.pixelpal.ui.home.mvi.HomeLocalDI
import myapplication.android.pixelpal.ui.platforms.fragments.platform.PlatformDetailsFragment
import myapplication.android.pixelpal.ui.platforms.fragments.platform.mvi.PlatformLocalDI
import javax.inject.Scope


@PlatformScope
@Subcomponent(
    modules = [PlatformsModule::class]
)
interface PlatformComponent {
    fun inject(fragment : PlatformDetailsFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): PlatformComponent
    }
}

@Module
class PlatformsModule{
    @PlatformScope
    @Provides
    fun providePlatformLocalDI(
        platformsRepository: PlatformsRepository
    ): PlatformLocalDI = PlatformLocalDI(platformsRepository)
}

@Scope
annotation class PlatformScope