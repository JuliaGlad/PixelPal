package myapplication.android.pixelpal.di.components.activity

import dagger.Subcomponent
import myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.StoreDetailsActivity


@StoreDetailsActivityScope
@Subcomponent
interface StoreDetailsActivityComponent {

    fun inject(activity: StoreDetailsActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create(): StoreDetailsActivityComponent
    }

}

annotation class StoreDetailsActivityScope