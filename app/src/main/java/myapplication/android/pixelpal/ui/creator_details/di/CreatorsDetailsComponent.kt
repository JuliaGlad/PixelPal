package myapplication.android.pixelpal.ui.creator_details.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.creator_details.CreatorDetailsFragment
import javax.inject.Scope

@CreatorsDetailsScope
@Component(
    modules = [
        CreatorsDetailsModule::class,
        CreatorsDetailsLocalDIModule::class
    ],
    dependencies = [AppComponent::class]
)
interface CreatorsDetailsComponent {

    fun inject(creatorDetailsFragment: CreatorDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): CreatorsDetailsComponent
    }
}

@Scope
annotation class CreatorsDetailsScope