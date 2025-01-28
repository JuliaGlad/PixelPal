package myapplication.android.pixelpal.di.components.activity

import dagger.Subcomponent
import myapplication.android.pixelpal.ui.creator_details.CreatorDetailsActivity
import myapplication.android.pixelpal.ui.profile.signing.AccountCreationActivity

@Subcomponent()
@CreateAccountActivityScope
interface CreateAccountActivityComponent {
    fun inject(activity: AccountCreationActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create(): CreateAccountActivityComponent
    }
}

annotation class CreateAccountActivityScope