package com.example.aptmentmanager.data.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.aptmentmanager.data.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import io.reactivex.Completable

class FirebaseSource {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun login(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(
                        // Tratar excecao aqui
                        it.exception!!
                    )
            }
        }
    }

    fun register(name: String, email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (!emitter.isDisposed) {
                if (task.isSuccessful) {
                    login(email, password)
                    firebaseAuth.currentUser?.let { firebaseUser ->
                        saveData(name, email, firebaseUser.uid)
                        Log.e("register", "testando o id do usuario ${firebaseUser.uid}")
                    }
                    logout()
                    emitter.onComplete()
                } else {
                    emitter.onError(
                        // Tratar excecao aqui
                        task.exception!!
                    )
                }
            }
        }

    }

    fun forgetPassword(email: String) = Completable.create { emitter ->
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(
                        // Tratar excecao aqui
                        it.exception!!
                    )
            }
        }
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

    private fun saveData(name: String, email: String, id: String) {
        val usuarios = hashMapOf("name" to name, "email" to email, "class" to 0, "photo" to "null")
        db.collection("Usuarios").document(id).set(usuarios).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.e("saveData", "${firebaseAuth.currentUser?.uid}")
            } else {
                // Tratar excecao aqui
                it.exception!!
            }
        }
    }

    fun getUid(): String {
        return firebaseAuth.currentUser?.uid.toString()
    }

    fun recoverLoginData(): LiveData<Usuario> {
        val liveData = MutableLiveData<Usuario>()
        db.collection("Usuarios").document(getUid()).get().addOnCompleteListener { document ->
            val usuario = document.result.toObject<Usuario>()
            liveData.value = usuario
        }
        return liveData
    }

    fun savePhoto(url : String) {
        db.collection("Usuarios").document(getUid()).update("photo", url)
    }
}