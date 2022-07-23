package com.example.aptmentmanager.data.repositories

import androidx.lifecycle.LiveData
import com.example.aptmentmanager.data.firebase.FirebaseSource
import com.example.aptmentmanager.data.models.Usuario

class UserRepository(
    private val firebase: FirebaseSource
) {
    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(name: String, email: String, password: String) =
        firebase.register(name, email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()

    fun forgetPassword(email: String) = firebase.forgetPassword(email)

    fun getUid() = firebase.getUid()

    fun recoverLoginData(): LiveData<Usuario> = firebase.recoverLoginData()

}