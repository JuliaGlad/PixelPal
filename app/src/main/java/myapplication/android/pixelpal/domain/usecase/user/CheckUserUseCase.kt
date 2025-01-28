package myapplication.android.pixelpal.domain.usecase.user

import myapplication.android.pixelpal.data.repository.user.FirebaseService

class CheckUserUseCase {
    fun checkAuth(): Boolean = FirebaseService.auth.currentUser != null
}