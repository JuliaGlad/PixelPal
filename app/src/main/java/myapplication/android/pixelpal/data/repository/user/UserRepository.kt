package myapplication.android.pixelpal.data.repository.user

import android.net.Uri

interface UserRepository {

    suspend fun getUserData(): UserDataModel

    suspend fun createAccount(email: String, password: String, name: String, image: Uri?)

    suspend fun signInWithEmailAndPassword(email: String, password: String)

    suspend fun deleteAccount(password: String)

    suspend fun editData(name: String)

    suspend fun logout()
}