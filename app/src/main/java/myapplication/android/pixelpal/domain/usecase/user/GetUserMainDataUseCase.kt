package myapplication.android.pixelpal.domain.usecase.user

import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.domain.mapper.user.toMainDomain
import javax.inject.Inject

class GetUserMainDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun invoke() = userRepository.getUserData().toMainDomain()
}