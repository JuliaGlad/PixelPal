package myapplication.android.pixelpal.ui.profile.login.mvi

import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.domain.usecase.user.SignInWithEmailAndPasswordUseCase
import javax.inject.Inject

class LoginLocalDI @Inject constructor(
    private val userRepository: UserRepository
) {

    private val signInWithEmailAndPasswordUseCase by lazy { SignInWithEmailAndPasswordUseCase(userRepository) }

    val actor by lazy { LoginActor(signInWithEmailAndPasswordUseCase) }

    val reducer by lazy { LoginReducer() }
}