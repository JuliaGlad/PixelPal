package myapplication.android.pixelpal.di.components.activity

import dagger.Subcomponent
import myapplication.android.pixelpal.ui.publisher_details.PublisherDetailsActivity

@PublisherDetailsActivityScope
@Subcomponent
interface PublisherDetailsActivityComponent{

    fun inject(activity: PublisherDetailsActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create(): PublisherDetailsActivityComponent
    }
}
annotation class PublisherDetailsActivityScope