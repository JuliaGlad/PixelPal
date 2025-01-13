package myapplication.android.pixelpal.di.components.activity

import dagger.Component
import dagger.Subcomponent

@PlatformDetailsActivity
@Subcomponent
interface PlatformDetailsActivityComponent {

    fun inject(activity: myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.PlatformDetailsActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create(): PlatformDetailsActivityComponent
    }
}
annotation class PlatformDetailsActivity