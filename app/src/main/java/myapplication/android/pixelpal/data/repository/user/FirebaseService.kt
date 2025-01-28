package myapplication.android.pixelpal.data.repository.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseService {

    companion object{
        var INSTANCE: FirebaseService? = null
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val storageReference: StorageReference = FirebaseStorage.getInstance().getReference()
        var fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    }

    fun getInstance(): FirebaseService? {
        if (INSTANCE == null) {
            INSTANCE = FirebaseService()
        }
        return INSTANCE
    }

}