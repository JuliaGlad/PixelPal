package myapplication.android.pixelpal.domain.usecase.user

import myapplication.android.pixelpal.data.repository.user.UserRepository
import javax.inject.Inject

class EditUserDataUserCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun invoke(name: String) = userRepository.editData(name)
}