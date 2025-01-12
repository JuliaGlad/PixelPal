package myapplication.android.pixelpal.di.components.activity

import dagger.Module
import dagger.Subcomponent
import myapplication.android.pixelpal.ui.creator_details.CreatorDetailsActivity

@Subcomponent()
@CreatorDetailsActivityScope
interface CreatorDetailsActivityComponent {
    fun inject(activity: CreatorDetailsActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create(): CreatorDetailsActivityComponent
    }
}

annotation class CreatorDetailsActivityScope