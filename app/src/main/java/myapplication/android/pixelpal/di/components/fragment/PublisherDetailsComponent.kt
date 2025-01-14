package myapplication.android.pixelpal.di.components.fragment

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import myapplication.android.pixelpal.data.repository.publishers.PublishersRepository
import myapplication.android.pixelpal.ui.publisher_details.PublisherDetailsFragment
import myapplication.android.pixelpal.ui.publisher_details.mvi.PublisherDetailsLocalDI

@PublisherDetailsScope
@Subcomponent(modules = [PublisherDetailsModule::class])
interface PublisherDetailsComponent {

    fun inject(fragment: PublisherDetailsFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): PublisherDetailsComponent
    }

}

@PublisherDetailsScope
@Module
class PublisherDetailsModule{

    @PublisherDetailsScope
    @Provides
    fun providePublisherDetailsLocalDI(
        publishersRepository: PublishersRepository
    ): PublisherDetailsLocalDI = PublisherDetailsLocalDI(publishersRepository)

}

annotation class PublisherDetailsScope