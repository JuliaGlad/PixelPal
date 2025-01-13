package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.platforms.PlatformsRepository
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.PlatformFullDetailsFragment
import myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.mvi.PlatformDetailsLocalDI

@PlatformDetailsScope
@Subcomponent(modules = [PlatformDetailsModule::class])
interface PlatformDetailsComponent {

    fun inject(fragment: PlatformFullDetailsFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): PlatformDetailsComponent
    }

}

@PlatformDetailsScope
@Module
class PlatformDetailsModule{

    @PlatformDetailsScope
    @Provides
    fun providePlatformDetailsLocalDI(
        platformsRepository: PlatformsRepository
    ): PlatformDetailsLocalDI = PlatformDetailsLocalDI(platformsRepository)

}

annotation class PlatformDetailsScope