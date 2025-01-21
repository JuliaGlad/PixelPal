package myapplication.android.pixelpal.di.components.activity

import dagger.Subcomponent
import myapplication.android.pixelpal.ui.all_creators.AllCreatorsActivity
import myapplication.android.pixelpal.ui.creator_details.CreatorDetailsActivity

@AllCreatorsActivityScope
@Subcomponent
interface AllCreatorsActivityComponent {
    fun inject(activity: AllCreatorsActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create(): AllCreatorsActivityComponent
    }
}

annotation class AllCreatorsActivityScope