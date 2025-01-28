package myapplication.android.pixelpal.ui.profile.main.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.pixelpal.domain.usecase.user.DeleteUserAccountUseCase
import myapplication.android.pixelpal.domain.usecase.user.EditUserDataUserCase
import myapplication.android.pixelpal.domain.usecase.user.GetUserMainDataUseCase
import myapplication.android.pixelpal.domain.usecase.user.SignOutUseCase
import myapplication.android.pixelpal.ui.ktx.asyncAwait
import myapplication.android.pixelpal.ui.mvi.MviActor
import myapplication.android.pixelpal.ui.profile.main.model.toUi

class ProfileMainActor(
    private val getUserMainDataUseCase: GetUserMainDataUseCase,
    private val deleteUserAccountUseCase: DeleteUserAccountUseCase,
    private val editUserDataUserCase: EditUserDataUserCase,
    private val signOutUseCase: SignOutUseCase
) : MviActor<
        ProfileMainPartialState,
        ProfileMainIntent,
        ProfileMainState,
        ProfileMainEffect>() {
    override fun resolve(
        intent: ProfileMainIntent,
        state: ProfileMainState
    ): Flow<ProfileMainPartialState> =
        when (intent) {
            is ProfileMainIntent.DeleteUser -> deleteUser(intent.password)
            ProfileMainIntent.GetUserData -> getUserData()
            ProfileMainIntent.Init -> init()
            ProfileMainIntent.Logout -> logout()
            is ProfileMainIntent.EditUser -> editUser(intent.name)
        }

    private fun init() = flow { emit(ProfileMainPartialState.Loading) }

    private fun logout() = flow {
        kotlin.runCatching {
            signOutUseCase.invoke()
        }.fold(
            onSuccess = {
                emit(ProfileMainPartialState.UserSignedOut)
            },
            onFailure = { throwable ->
                emit(ProfileMainPartialState.Error(throwable))
            }
        )
    }

    private fun editUser(name: String) = flow {
        kotlin.runCatching {
            editUserDataUserCase.invoke(name)
        }.fold(
            onSuccess ={
                emit(ProfileMainPartialState.DataUpdated(name))
            },
            onFailure = { throwable ->
                emit(ProfileMainPartialState.Error(throwable))
            }
        )
    }

    private fun deleteUser(password: String) = flow {
        kotlin.runCatching {
            deleteUserAccountUseCase.invoke(password)
        }.fold(
            onSuccess ={
                emit(ProfileMainPartialState.AccountDeleted)
            },
            onFailure = { throwable ->
                emit(ProfileMainPartialState.Error(throwable))
            }
        )
    }

    private fun getUserData() =
        flow {
            kotlin.runCatching {
                asyncAwait({
                    getUserMainDataUseCase.invoke()
                }) { data ->
                    data.toUi()
                }
            }.fold(
                onSuccess = { data ->
                    emit(ProfileMainPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(ProfileMainPartialState.Error(throwable))
                }
            )
        }
}