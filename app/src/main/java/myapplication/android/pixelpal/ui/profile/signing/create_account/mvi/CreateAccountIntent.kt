package myapplication.android.pixelpal.ui.profile.signing.create_account.mvi

import android.net.Uri
import myapplication.android.pixelpal.ui.mvi.MviIntent

sealed interface CreateAccountIntent: MviIntent {

    data object Init: CreateAccountIntent

    data class CreateAccount(
        val email: String,
        val password: String,
        val name: String,
        val uri: Uri?
    ): CreateAccountIntent
}