package myapplication.android.pixelpal.data.repository.user

import android.net.Uri
import myapplication.android.pixelpal.data.repository.dto.user.UserDataDto

interface UserRepository {

    suspend fun getUserData(): UserDataDto

    suspend fun createAccount(email: String, password: String, name: String, image: Uri?)

    suspend fun signInWithEmailAndPassword(email: String, password: String)

    suspend fun deleteAccount(password: String)

    suspend fun editData(name: String)

    suspend fun logout()
}