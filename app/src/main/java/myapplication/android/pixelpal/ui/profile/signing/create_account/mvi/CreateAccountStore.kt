package myapplication.android.pixelpal.ui.profile.signing.create_account.mvi

import myapplication.android.pixelpal.ui.mvi.MviStore

class CreateAccountStore(
    reducer: CreateAccountReducer,
    actor: CreateAccountActor
): MviStore<
        CreateAccountPartialState,
        CreateAccountIntent,
        CreateAccountState,
        CreateAccountEffect>(reducer, actor) {
    override fun initialStateCreator(): CreateAccountState = CreateAccountState(CreateAccountLceState.Init)
}