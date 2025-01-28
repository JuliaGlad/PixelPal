package myapplication.android.pixelpal.ui.profile.login.mvi

import myapplication.android.pixelpal.ui.mvi.MviReducer

class LoginReducer: MviReducer<
        LoginPartialState,
        LoginState> {
    override fun reduce(prevState: LoginState, partialState: LoginPartialState): LoginState =
        when(partialState){
            is LoginPartialState.Error -> updateError(prevState, partialState.throwable)
            LoginPartialState.Init -> updateInit()
            LoginPartialState.Loading -> updateLoading(prevState)
            LoginPartialState.LoggedIn -> updateLoggedIn(prevState)
        }

    private fun updateInit() = LoginState(LoginLceState.Init)

    private fun updateError(prevState: LoginState, throwable: Throwable) = prevState.copy(
        ui = LoginLceState.Error(throwable)
    )

    private fun updateLoggedIn(prevState: LoginState) = prevState.copy(ui = LoginLceState.LoggedIn)

    private fun updateLoading(prevState: LoginState) = prevState.copy(ui = LoginLceState.Loading)
}