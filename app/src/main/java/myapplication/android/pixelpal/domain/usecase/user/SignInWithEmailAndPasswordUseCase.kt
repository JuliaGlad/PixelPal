package myapplication.android.pixelpal.domain.usecase.user

import myapplication.android.pixelpal.data.repository.user.UserRepository
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun invoke(email: String, password: String) = userRepository.signInWithEmailAndPassword(email, password)
}