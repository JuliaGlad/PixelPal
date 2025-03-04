package myapplication.android.pixelpal.data.repository.user

import android.net.Uri
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.data.repository.dto.user.UserDataDto
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {

    override suspend fun getUserData(): UserDataDto =
        withContext(Dispatchers.IO) {
            val uid = FirebaseService.auth.currentUser!!.uid
            val result = FirebaseService.fireStore
                .collection(USER_COLLECTION)
                .document(uid)
                .get()
                .await()

            UserDataDto(
                uid,
                result.getString(USER_NAME),
                result.getString(USER_EMAIL)
            )
        }

    override suspend fun createAccount(email: String, password: String, name: String, image: Uri?) {
        withContext(Dispatchers.IO) {
            FirebaseService.auth.createUserWithEmailAndPassword(
                email, password
            ).await()
            FirebaseService.auth.currentUser?.let { addUserToStore(it.uid, name, email).await() }
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        withContext(Dispatchers.IO) {
            FirebaseService.auth.signInWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun deleteAccount(password: String) {
        withContext(Dispatchers.IO) {
            val user = FirebaseService.auth.currentUser
            val credential = user?.email?.let { EmailAuthProvider.getCredential(it, password) }
            user?.reauthenticate(credential!!)?.await()
            FirebaseService.auth.currentUser?.delete()?.await()
        }
    }

    override suspend fun editData(name: String) {
        withContext(Dispatchers.IO) {
            FirebaseService.auth.currentUser?.uid?.let { uid ->
                FirebaseService.fireStore
                    .collection(USER_COLLECTION)
                    .document(uid)
                    .update(USER_NAME, name
                    ).await()
            }
        }
    }

    override suspend fun logout() {
        FirebaseService.auth.signOut()
    }

    private fun addUserToStore(
        userId: String,
        name: String,
        email: String
    ) = FirebaseService.fireStore
        .collection(USER_COLLECTION)
        .document(userId)
        .set(
            hashMapOf(
                USER_NAME to name,
                USER_EMAIL to email,
                FAVORITE_GAMES to listOf<Long>()
            )
        )

    companion object {
        const val FAVORITE_GAMES = "FavoriteGames"
        const val USER_COLLECTION = "UserCollection"
        const val USER_NAME = "UserName"
        const val USER_EMAIL = "UserEmail"
    }
}