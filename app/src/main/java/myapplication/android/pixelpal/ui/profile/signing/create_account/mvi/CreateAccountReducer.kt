package myapplication.android.pixelpal.ui.profile.signing.create_account.mvi

import myapplication.android.pixelpal.ui.mvi.MviReducer

class CreateAccountReducer: MviReducer<
        CreateAccountPartialState,
        CreateAccountState> {
    override fun reduce(
        prevState: CreateAccountState,
        partialState: CreateAccountPartialState
    ): CreateAccountState =
        when(partialState){
            CreateAccountPartialState.AccountCreated -> updateAccountCreate(prevState)
            is CreateAccountPartialState.Error -> updateError(prevState, partialState.throwable)
            CreateAccountPartialState.Init -> updateInit()
            CreateAccountPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateError(prevState: CreateAccountState, throwable: Throwable) =
        prevState.copy(ui = CreateAccountLceState.Error(throwable))

    private fun updateLoading(prevState: CreateAccountState) = prevState.copy(ui = CreateAccountLceState.Loading)

    private fun updateAccountCreate(prevState: CreateAccountState) = prevState.copy(
        ui = CreateAccountLceState.AccountCreated
    )

    private fun updateInit() = CreateAccountState(CreateAccountLceState.Init)
}