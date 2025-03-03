package myapplication.android.pixelpal.ui.publisher_details.di

import dagger.Component
import myapplication.android.pixelpal.di.AppComponent
import myapplication.android.pixelpal.ui.publisher_details.PublisherDetailsFragment
import javax.inject.Scope

@PublisherDetailsScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        PublisherDetailsLocalDIModule::class,
        PublisherDetailsModule::class
    ]
)
interface PublisherDetailsComponent {

    fun inject(publisherDetailsFragment: PublisherDetailsFragment)

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): PublisherDetailsComponent
    }
}

@Scope
annotation class PublisherDetailsScope