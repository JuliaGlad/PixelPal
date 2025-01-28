package myapplication.android.pixelpal.domain.usecase.user

import myapplication.android.pixelpal.data.repository.user.UserRepository
import javax.inject.Inject

class DeleteUserAccountUseCase @Inject constructor(
    val userRepository: UserRepository
) {
    suspend fun invoke(password: String) = userRepository.deleteAccount(password)
}