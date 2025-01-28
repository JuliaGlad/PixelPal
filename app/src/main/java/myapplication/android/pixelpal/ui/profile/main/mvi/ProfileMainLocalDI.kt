package myapplication.android.pixelpal.ui.profile.main.mvi

import myapplication.android.pixelpal.data.repository.user.UserRepository
import myapplication.android.pixelpal.domain.usecase.user.DeleteUserAccountUseCase
import myapplication.android.pixelpal.domain.usecase.user.EditUserDataUserCase
import myapplication.android.pixelpal.domain.usecase.user.GetUserMainDataUseCase
import myapplication.android.pixelpal.domain.usecase.user.SignOutUseCase
import javax.inject.Inject

class ProfileMainLocalDI @Inject constructor(
    private val userRepository: UserRepository
) {
    private val getUserMainDataUseCase by lazy { GetUserMainDataUseCase(userRepository) }

    private val deleteUserAccountUseCase by lazy { DeleteUserAccountUseCase(userRepository) }

    private val editUserDataUseCase by lazy { EditUserDataUserCase(userRepository) }

    private val signOutUseCase by lazy { SignOutUseCase(userRepository) }

    val actor by lazy { ProfileMainActor(getUserMainDataUseCase, deleteUserAccountUseCase, editUserDataUseCase, signOutUseCase) }

    val reducer by lazy { ProfileMainReducer() }
}