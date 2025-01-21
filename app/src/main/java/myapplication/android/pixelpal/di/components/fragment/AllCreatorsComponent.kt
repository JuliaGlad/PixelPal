package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.creators.CreatorsRepository
import myapplication.android.pixelpal.ui.all_creators.AllCreatorsFragment
import myapplication.android.pixelpal.ui.all_creators.mvi.AllCreatorsLocalDI

@AllCreatorsScope
@Subcomponent(modules = [AllCreatorsModule::class])
interface AllCreatorsComponent {
    fun inject(fragment: AllCreatorsFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): AllCreatorsComponent
    }
}

@AllCreatorsScope
@Module
class AllCreatorsModule{

    @AllCreatorsScope
    @Provides
    fun provideAllCreatorsLocalDI(
        creatorsRepository: CreatorsRepository
    ): AllCreatorsLocalDI = AllCreatorsLocalDI(creatorsRepository)
}

annotation class AllCreatorsScope