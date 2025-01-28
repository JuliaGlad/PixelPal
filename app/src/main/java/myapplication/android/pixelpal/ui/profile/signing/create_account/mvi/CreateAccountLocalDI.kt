package myapplication.android.pixelpal.ui.profile.signing.create_account.mvi

import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.domain.usecase.user.CreateUserAccountUseCase
import javax.inject.Inject

class CreateAccountLocalDI @Inject constructor(
    private val userRepository: UserRepository
){
    private val createUserAccountUseCase by lazy { CreateUserAccountUseCase(userRepository) }

    val actor by lazy { CreateAccountActor(createUserAccountUseCase) }

    val reducer by lazy { CreateAccountReducer() }
}