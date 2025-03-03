package myapplication.android.pixelpal.ui.creators.di

import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.creators.CreatorsFragment
import myapplication.android.pixelpal.ui.creators.CreatorsViewModel
import javax.inject.Scope

@CreatorsScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CreatorsModule::class,
        CreatorsLocalDIModule::class
    ]
)
interface CreatorsComponent {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun creatorsViewModelFactory(): CreatorsViewModel.CreatorsViewModelAssistedFactory

    fun inject(creatorsFragment: CreatorsFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): CreatorsComponent
    }
}

@Scope
annotation class CreatorsScope