package com.example.aptmentmanager.data.repositories

import com.example.aptmentmanager.data.firebase.FirebaseSource

class UserRepository(
    private val firebase: FirebaseSource
) {
    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()

    fun forgetPassword(email: String) = firebase.forgetPassword(email)

    fun saveData(name: String, email: String, uid: String) = firebase.saveData(name, email, uid)

    fun getUid() = firebase.getUid()

}