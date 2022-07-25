package com.example.aptmentmanager.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.aptmentmanager.data.models.Usuario
import com.example.aptmentmanager.data.repositories.UserRepository

class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private var uid: String? = null

    private val user by lazy {
        repository.currentUser()
    }

    fun logout() {
        repository.logout()
    }

    fun logged(): Boolean {

        return repository.currentUser() != null
    }

    private fun getId(): String? {
        if (user != null) {
            uid = repository.getUid()
        }
        return uid
    }

    fun recoverDataLogin(): LiveData<Usuario> {

        return repository.recoverLoginData()
    }

    fun savePhoto(url : String) {
        repository.savePhoto(url)
    }

}