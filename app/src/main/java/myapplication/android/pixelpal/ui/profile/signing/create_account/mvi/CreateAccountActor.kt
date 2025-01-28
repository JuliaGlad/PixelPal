package myapplication.android.pixelpal.ui.profile.signing.create_account.mvi

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.user.CreateUserAccountUseCase
import myapplication.android.pixelpal.ui.mvi.MviActor

class CreateAccountActor(
    private val createUserAccountUseCase: CreateUserAccountUseCase
) : MviActor<
        CreateAccountPartialState,
        CreateAccountIntent,
        CreateAccountState,
        CreateAccountEffect>() {
    override fun resolve(
        intent: CreateAccountIntent,
        state: CreateAccountState
    ): Flow<CreateAccountPartialState> =
        when (intent) {
            CreateAccountIntent.Init -> init()
            is CreateAccountIntent.CreateAccount ->
                with(intent) {
                    createAccount(
                        email,
                        password,
                        name,
                        uri
                    )
                }
        }

    private fun init() = flow { emit(CreateAccountPartialState.Init) }

    private fun createAccount(
        email: String,
        password: String,
        name: String,
        uri: Uri?
    ) = flow {
        kotlin.runCatching {
            createUserAccountUseCase.invoke(email, password, name, uri)
        }.fold(
            onSuccess = {
                emit(CreateAccountPartialState.AccountCreated)
            },
            onFailure = { throwable ->
                emit(CreateAccountPartialState.Error(throwable))
            }
        )
    }
}