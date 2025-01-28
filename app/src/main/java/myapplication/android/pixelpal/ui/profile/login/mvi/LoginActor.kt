package myapplication.android.pixelpal.ui.profile.login.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.user.SignInWithEmailAndPasswordUseCase
import myapplication.android.pixelpal.ui.mvi.MviActor

class LoginActor(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase
): MviActor<
        LoginPartialState,
        LoginIntent,
        LoginState,
        LoginEffect>() {
    override fun resolve(intent: LoginIntent, state: LoginState): Flow<LoginPartialState> =
        when(intent){
            LoginIntent.Init -> init()
            is LoginIntent.SignInWithEmailAndPassword -> signInWithEmailAndPassword(
                intent.email,
                intent.password
            )
        }
    private fun init() = flow { emit(LoginPartialState.Init) }

    private fun signInWithEmailAndPassword(email: String, password: String) =
        flow {
            kotlin.runCatching {
                signInWithEmailAndPasswordUseCase.invoke(email, password)
            }.fold(
                onSuccess = { emit(LoginPartialState.LoggedIn) },
                onFailure = {throwable -> emit(LoginPartialState.Error(throwable))}
            )
        }
}