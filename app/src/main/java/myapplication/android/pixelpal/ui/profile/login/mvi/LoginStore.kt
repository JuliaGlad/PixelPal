package myapplication.android.pixelpal.ui.profile.login.mvi

import myapplication.android.pixelpal.ui.mvi.MviStore

class LoginStore(
    reducer: LoginReducer,
    actor: LoginActor
): MviStore<
        LoginPartialState,
        LoginIntent,
        LoginState,
        LoginEffect>(reducer, actor) {
    override fun initialStateCreator(): LoginState = LoginState(LoginLceState.Init)
}