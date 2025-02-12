package myapplication.android.pixelpal.data.repository.user

import android.net.Uri
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.app.Constants
import myapplication.android.pixelpal.data.repository.dto.user.UserDataDto
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {

    override suspend fun getUserData(): UserDataDto =
        withContext(Dispatchers.IO){
            val uid = FirebaseService.auth.currentUser!!.uid
            val result = FirebaseService.fireStore
                .collection(Constants.USER_COLLECTION)
                .document(uid)
                .get()
                .await()

            UserDataDto(
                uid,
                result.getString(Constants.USER_NAME),
                result.getString(Constants.USER_EMAIL)
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
        withContext(Dispatchers.IO){
            FirebaseService.auth.currentUser?.uid?.let { uid ->
                FirebaseService.fireStore
                    .collection(Constants.USER_COLLECTION)
                    .document(uid)
                    .update(
                        Constants.USER_NAME, name
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
            .collection(Constants.USER_COLLECTION)
            .document(userId)
            .set(
                hashMapOf(
                    Constants.USER_NAME to name,
                    Constants.USER_EMAIL to email
                )
            )
}