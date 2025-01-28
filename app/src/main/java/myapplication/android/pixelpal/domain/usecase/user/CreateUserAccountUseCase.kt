package myapplication.android.pixelpal.domain.usecase.user

import android.net.Uri
import myapplication.android.pixelpal.data.repository.user.UserRepository
import javax.inject.Inject

class CreateUserAccountUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun invoke(email: String, password: String,name: String, image: Uri?) = userRepository.createAccount(
        email, password, name, image
    )
}